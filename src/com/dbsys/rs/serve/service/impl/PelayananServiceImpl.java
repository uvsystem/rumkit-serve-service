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

		Pasien pasien = pasienRepository.findOne(pelayanan.getPasien().getId());
		pasien.addTotalTagihan(pelayanan.getTagihan());
		pelayanan.setPasien(pasien);
		
		pelayanan = pelayananRepository.save(pelayanan);
		
		return pelayanan;
	}

	@Override
	@Transactional(readOnly = false)
	public void masuk(PelayananTemporal pelayanan) {
		PelayananTemporal pelayananOld = pelayanan.getPasien().getPerawatan();
		if (pelayananOld != null) {
			pelayananOld.setTanggalSelesai(DateUtil.getDate());
			pelayananOld.setJamKeluar(DateUtil.getTime());
			
			pelayananOld = pelayananRepository.save(pelayananOld);
		}

		if (pelayanan.getTanggal() == null)
			pelayanan.setTanggal(DateUtil.getDate());

		if (pelayanan.getJamMasuk() == null)
			pelayanan.setJamMasuk(DateUtil.getTime());

		if (TipeUnit.RUANG_PERAWATAN.equals(pelayanan.getUnit().getTipe())) {
			pelayanan.getPasien().setTipePerawatan(Perawatan.RAWAT_INAP);
			pelayanan.getPasien().setTanggalRawatInap(DateUtil.getDate());
		}

		pelayanan.getPasien().setPerawatan(pelayanan);
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);
		pelayanan = pelayananRepository.save(pelayanan);
	}

	@Override
	@Transactional(readOnly = false)
	public Pelayanan keluar(Long id, Date tanggal, Time jam, Long tambahan, String keterangan) {
		if (tanggal == null)
			tanggal = DateUtil.getDate();
		
		if (jam == null)
			jam = DateUtil.getTime();
		
		Pasien pasien = pasienRepository.findOne(id);

		PelayananTemporal pelayanan = pasien.getPerawatan();
		pelayanan.setTanggalSelesai(tanggal);
		pelayanan.setJamKeluar(jam);
		pelayanan.setBiayaTambahan(tambahan);
		pelayanan.setKeterangan(keterangan);

		pasien.addTotalTagihan(pelayanan.getTagihan());
		pelayanan.setPasien(pasien);
		
		pelayanan = pelayananRepository.save(pelayanan);

		return pelayanan;
	}

	@Override
	@Transactional(readOnly = false)
	public Pasien hapus(Long id) {
		Pelayanan pelayanan = pelayananRepository.findOne(id);
		pelayananRepository.delete(id);

		Pasien pasien = pasienRepository.findOne(pelayanan.getId());
		pasien.substractTotalTagihan(pelayanan.getTagihan());
		
		pasien = pasienRepository.save(pasien);
		
		return pasien;
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
