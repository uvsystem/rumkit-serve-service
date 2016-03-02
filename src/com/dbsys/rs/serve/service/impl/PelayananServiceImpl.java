package com.dbsys.rs.serve.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.DateUtil;
import com.dbsys.rs.NumberException;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.PasienOutException;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pelayanan;
import com.dbsys.rs.serve.entity.PelayananTemporal;
import com.dbsys.rs.serve.entity.Pasien.Perawatan;
import com.dbsys.rs.serve.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.serve.entity.Unit.TipeUnit;
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
	public Pelayanan simpan(Pelayanan pelayanan) throws PasienOutException, NumberException {
		/*
		 * Jumlah pelayanan harus lebih dari 0.
		 * Jika tidak sistem akan menolak.
		 */
		if (pelayanan.getJumlah() <= 0)
			throw new NumberException("Jumlah tindakan tidak boleh 0");
			
		Pasien pasien = pasienRepository.findOne(pelayanan.getPasien().getId());
		
		/*
		 * Pelayanan tidak bisa ditambahkan pada pasien yang sudah keluar.
		 * Sistem akan langsung menolak.
		 */
		if (Pasien.StatusPasien.KELUAR.equals(pasien.getStatus()))
			throw new PasienOutException("Tidak bisa menambahan tagihan untuk pasien yang sudah keluar");

		if (pelayanan.getTanggal() == null)
			pelayanan.setTanggal(DateUtil.getDate());

		if (pelayanan.getStatus() == null)
			pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		/*
		 * Jika pelayanan PERSISTED (merupakan fungsi update),
		 * kurangi total tagihan pasien, sesuai tagihan pelayanan yang lama.
		 */
		if (pelayanan.isPersisted()) {
			Pelayanan pelayananOld = pelayananRepository.findOne(pelayanan.getId());
			
			pasien.substractTotalTagihan(pelayananOld.getTagihan());
		}

		/*
		 * Tambahkan tagihan pelayanan yang baru ke total tagihan pasien.
		 */
		pasien.addTotalTagihan(pelayanan.getTagihan());
		pelayanan.setPasien(pasien);
		
		pelayanan = pelayananRepository.save(pelayanan);
		
		return pelayanan;
	}

	@Override
	@Transactional(readOnly = false)
	public void masuk(PelayananTemporal pelayanan) throws PasienOutException {
		Pasien pasien = pasienRepository.findOne(pelayanan.getPasien().getId());
		/*
		 * Pelayanan tidak bisa ditambahkan pada pasien yang sudah keluar.
		 * Sistem akan menolak.
		 */
		if (Pasien.StatusPasien.KELUAR.equals(pasien.getStatus()))
			throw new PasienOutException("Tidak bisa menambahan tagihan untuk pasien yang sudah keluar");

		/*
		 * Update pelayanan temporal yang lama.
		 * Jika pasien berasal dari ruangan lain, 
		 * maka update pelayanan temporal lama dan simpan.
		 */
		PelayananTemporal pelayananOld = pasien.getPerawatan();
		if (pelayananOld != null) {
			pelayananOld.setTanggalSelesai(DateUtil.getDate());
			pelayananOld.setJamKeluar(DateUtil.getTime());

			pasien.addTotalTagihan(pelayananOld.getTagihan());
			pelayananOld.setPasien(pasien);
			
			pelayananOld = pelayananRepository.save(pelayananOld);
		}

		pasien.setPerawatan(pelayanan);

		/*
		 * Jika pasien bukan pasien rawat inap dan unit yang menginput merupakan
		 * RUANG PERAWATAN, maka ubah tipe perawatan menjadi RAWAT INAP dan
		 * tanggal rawat inap menjadi hari ini.
		 */
		if (TipeUnit.RUANG_PERAWATAN.equals(pelayanan.getUnit().getTipe()) &&
				!(Pasien.Perawatan.RAWAT_INAP.equals(pasien.getTipePerawatan()))) {
			pasien.setTipePerawatan(Perawatan.RAWAT_INAP);
			pasien.setTanggalRawatInap(DateUtil.getDate());
		}

		if (pelayanan.getTanggal() == null)
			pelayanan.setTanggal(DateUtil.getDate());

		if (pelayanan.getJamMasuk() == null)
			pelayanan.setJamMasuk(DateUtil.getTime());

		pelayanan.setPasien(pasien);
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

		/*
		 * Pasien dinyatakan keluar dari ruangan.
		 */
		pasien.setPerawatan(null);

		/*
		 * Tambah tagihan pelayanan ke total tagihan pasien.
		 */
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

		Pasien pasien = pasienRepository.findOne(pelayanan.getPasien().getId());
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

	@Override
	public List<Pelayanan> get(Date awal, Date akhir, Penanggung penanggung) {
		if (Penanggung.BPJS.equals(penanggung))
			return pelayananRepository.findByTanggalBetweenAndPasien_PenanggungAndTindakan_PenanggungOrderByPasien_KodeAsc(awal, akhir, penanggung, penanggung);
		return pelayananRepository.findByTanggalBetweenAndPasien_PenanggungOrTindakan_PenanggungOrderByPasien_KodeAsc(awal, akhir, penanggung, penanggung);
	}
}
