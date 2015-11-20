package com.dbsys.rs.serve.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.lib.entity.PelayananTemporal;
import com.dbsys.rs.lib.entity.Pasien.Perawatan;
import com.dbsys.rs.lib.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.lib.entity.Unit.TipeUnit;
import com.dbsys.rs.serve.repository.PasienRepository;
import com.dbsys.rs.serve.repository.PelayananRepository;
import com.dbsys.rs.serve.service.PelayananService;

@Service
@Transactional(readOnly = true)
public class PelayananServiceImpl implements PelayananService {

	@Autowired
	private PelayananRepository pelayananRepository;
	@Autowired
	private PasienRepository pasienRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pelayanan simpan(Pelayanan pelayanan) {
		if (pelayanan.getTanggal() == null)
			pelayanan.setTanggal(DateUtil.getDate());

		if (pelayanan.getStatus() == null)
			pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		Pasien pasien = pelayanan.getPasien();

		pelayanan = pelayananRepository.save(pelayanan);

		long tagihanPasien = pasien.getTotalTagihan() == null ? 0 : pasien.getTotalTagihan();
		long totalTagihan = tagihanPasien + pasien.getTotalTagihan();
		pasienRepository.updateTagihan(pasien.getId(), totalTagihan);
		
		return pelayanan;
	}

	@Override
	@Transactional(readOnly = false)
	public void masuk(PelayananTemporal pelayanan) {
		if (pelayanan.getTanggal() == null)
			pelayanan.setTanggal(DateUtil.getDate());

		if (pelayanan.getJamMasuk() == null)
			pelayanan.setJamMasuk(DateUtil.getTime());

		pelayanan.setStatus(StatusTagihan.MENUNGGAK);
		pelayanan = pelayananRepository.save(pelayanan);

		PelayananTemporal pelayananOld = pelayanan.getPasien().getPerawatan();
		if (pelayananOld != null) {
			pelayananOld.setTanggalSelesai(DateUtil.getDate());
			pelayananOld.setJamKeluar(DateUtil.getTime());
			
			pelayananOld = pelayananRepository.save(pelayananOld);
		}
		
		if (TipeUnit.UGD.equals(pelayanan.getUnit().getTipe())) {
			pasienRepository.convert(pelayanan.getPasien().getId(), pelayanan);
		} else {
			Date tanggalRawatInap = DateUtil.getDate();
			pasienRepository.convert(pelayanan.getPasien().getId(), Perawatan.RAWAT_INAP, pelayanan, tanggalRawatInap);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void keluar(Long id, Date tanggal, Time jam, Long tambahan, String keterangan) {
		Pasien pasien = pasienRepository.findOne(id);

		if (tanggal == null)
			tanggal = DateUtil.getDate();
		
		if (jam == null)
			jam = DateUtil.getTime();
		
		PelayananTemporal pelayanan = pasien.getPerawatan();
		pelayanan.setTanggalSelesai(tanggal);
		pelayanan.setJamKeluar(jam);
		pelayanan.getTagihan();
		
		int jumlah = pelayanan.getJumlah();
		
		pelayananRepository.update(pasien, tanggal, jam, tambahan, keterangan, jumlah);

		long tagihanPasien = pasien.getTotalTagihan() == null ? 0 : pasien.getTotalTagihan();
		long totalTagihan = tagihanPasien + pasien.getTotalTagihan();
		pasienRepository.updateTagihan(pasien.getId(), totalTagihan, null);
	}

	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		Pelayanan pelayanan = pelayananRepository.findOne(id);
		Pasien pasien = pelayanan.getPasien();
		long tagihanPasien = pasien.getTotalTagihan() == null ? 0 : pasien.getTotalTagihan();
		long totalTagihan = tagihanPasien - pelayanan.getTagihan();
		
		if (pelayanan.equals(pasien.getPerawatan())) {
			pasienRepository.updateTagihan(pasien.getId(), totalTagihan, null);
		} else {
			pasienRepository.updateTagihan(pasien.getId(), totalTagihan);
		}
		
		pelayananRepository.delete(id);
	}

	@Override
	public Pelayanan getById(Long id) {
		return pelayananRepository.findOne(id);
	}

	@Override
	public List<Pelayanan> getByPasien(Long id) {
		return pelayananRepository.findByPasien_Id(id);
	}
}
