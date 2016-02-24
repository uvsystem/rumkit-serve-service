package com.dbsys.rs.serve.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.DateUtil;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pembayaran;
import com.dbsys.rs.serve.entity.Tagihan;
import com.dbsys.rs.serve.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.serve.repository.PasienRepository;
import com.dbsys.rs.serve.repository.PembayaranRepository;
import com.dbsys.rs.serve.service.PembayaranService;

@Service
@Transactional(readOnly = true)
public class PembayaranServiceImpl implements PembayaranService {

	@Autowired
	private PembayaranRepository pembayaranRepository;
	@Autowired
	private PasienRepository pasienRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pembayaran simpan(Pembayaran pembayaran) {
		if (pembayaran.getKode() == null || pembayaran.getKode().equals("")) {
			String kode = pembayaran.generateKode();
			pembayaran.setKode(kode);
		}
		
		if (pembayaran.getTanggal() == null)
			pembayaran.setTanggal(DateUtil.getDate());
		
		if (pembayaran.getJam() == null)
			pembayaran.setJam(DateUtil.getTime());
		
		/*
		 * Update property pembayaran & statusTagihan pada tagihan,
		 * agar diidentifikasi sudah sibayar.
		 */
		for (Tagihan tagihan : pembayaran.getListTagihan()) {
			tagihan.setPembayaran(pembayaran);
			tagihan.setStatus(StatusTagihan.LUNAS);
		}
		
		pembayaran = pembayaranRepository.save(pembayaran);
		
		Pasien pasien = pasienRepository.findOne(pembayaran.getPasien().getId());
		pasien.addCicilan(pembayaran.getJumlah());
		pembayaran.setPasien(pasien);
		pasienRepository.updateCicilan(pasien.getId(), pasien.getCicilan());

		return pembayaran;
	}

	@Override
	public Pembayaran get(String kode) {
		return pembayaranRepository.findOne(kode);
	}

	@Override
	public List<Pembayaran> get(Long pasien) {
		return pembayaranRepository.findByPasien_Id(pasien);
	}

	@Override
	public List<Pembayaran> get(Date awal, Date akhir) {
		return pembayaranRepository.findByTanggalBetween(awal, akhir);
	}
}
