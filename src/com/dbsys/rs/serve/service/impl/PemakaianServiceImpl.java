package com.dbsys.rs.serve.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.DateUtil;
import com.dbsys.rs.NumberException;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.PasienOutException;
import com.dbsys.rs.serve.entity.Barang;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pemakaian;
import com.dbsys.rs.serve.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.serve.repository.BarangRepository;
import com.dbsys.rs.serve.repository.PasienRepository;
import com.dbsys.rs.serve.repository.PemakaianRepository;
import com.dbsys.rs.serve.service.PemakaianService;

@Service
@Transactional(readOnly = true)
public class PemakaianServiceImpl implements PemakaianService {

	@Autowired
	private PemakaianRepository pemakaianRepository;
	@Autowired
	private BarangRepository barangRepository;
	@Autowired
	private PasienRepository pasienRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pemakaian simpan(Pemakaian pemakaian) throws PasienOutException, NumberException {
		/*
		 * Jumlah pemakaian harus lebih dari 0.
		 */
		if (pemakaian.getJumlah() <= 0)
			throw new NumberException("Jumlah pemakaian harus lebih dari 0");
		
		Pasien pasien = pasienRepository.findOne(pemakaian.getPasien().getId());
		/*
		 * Jika pasien sudah keluar, tidak bisa menambah pemakaian.
		 */
		if (Pasien.StatusPasien.KELUAR.equals(pasien.getStatus()))
			throw new PasienOutException("Tidak bisa menambah tagihan untuk pasien yang sudah keluar");

		Barang barang = barangRepository.findOne(pemakaian.getBarang().getId());

		/*
		 * Jika pemakaian PERSISTED (merupakan fungsi update),
		 * kurangi total tagihan pasien, sesuai tagihan pemakaian yang lama.
		 * Tambahkan jumlah barang, sesuai pemakaian lama.
		 */
		if (pemakaian.isPersisted()) {
			Pemakaian pemakaianOld = pemakaianRepository.findOne(pemakaian.getId());
			
			pasien.substractTotalTagihan(pemakaianOld.getTagihan());
			barang.add(pemakaianOld.getJumlah());
		}

		/*
		 * Kurangi jumlah barang, sesuai jumlah pemakaian.
		 */
		barang.substract(pemakaian.getJumlah());
		pemakaian.setBarang(barang);

		/*
		 * Tambahkan tagihan pemakaian yang baru ke total tagihan pasien.
		 */
		pasien.addTotalTagihan(pemakaian.getTagihan());
		pemakaian.setPasien(pasien);

		if (pemakaian.getTanggal() == null)
			pemakaian.setTanggal(DateUtil.getDate());

		if (pemakaian.getStatus() == null)
			pemakaian.setStatus(StatusTagihan.MENUNGGAK);

		pemakaianRepository.save(pemakaian);

		return pemakaian;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void hapus(Long id) {
		Pemakaian pemakaian = pemakaianRepository.findOne(id);

		Barang barang = barangRepository.findOne(pemakaian.getBarang().getId());
		long jumlahBarang = barang.getJumlah() + pemakaian.getJumlah();
		barangRepository.updateJumlah(barang.getId(), jumlahBarang);

		Pasien pasien = pasienRepository.findOne(pemakaian.getPasien().getId());
		long tagihanPasien = pasien.getTotalTagihan() == null ? 0 : pasien.getTotalTagihan();
		long totalTagihan = tagihanPasien - pemakaian.getTagihan();
		pasienRepository.updateTagihan(pasien.getId(), totalTagihan);

		pemakaianRepository.delete(id);
	}

	@Override
	public Pemakaian getById(Long id) {
		return pemakaianRepository.findOne(id);
	}

	@Override
	public List<Pemakaian> getByPasien(Long id) {
		return pemakaianRepository.findByPasien_Id(id);
	}

	@Override
	public List<Pemakaian> getByNomorResep(String nomorResep) {
		return pemakaianRepository.findByNomorResep(nomorResep);
	}

	@Override
	public List<Pemakaian> get(Date awal, Date akhir, Penanggung penanggung) {
		if (Penanggung.BPJS.equals(penanggung))
			return pemakaianRepository.findByTanggalBetweenAndPasien_PenanggungAndBarang_PenanggungOrderByPasien_KodeAsc(awal, akhir, penanggung, penanggung);
		return pemakaianRepository.findByTanggalBetweenAndPasien_PenanggungOrBarang_PenanggungOrderByPasien_KodeAsc(awal, akhir, penanggung, penanggung);
	}
}
