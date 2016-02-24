package com.dbsys.rs.serve.service;

import java.sql.Date;
import java.util.List;

import com.dbsys.rs.serve.entity.Pembayaran;

public interface PembayaranService {

	Pembayaran simpan(Pembayaran pembayaran);

	Pembayaran get(String kode);

	List<Pembayaran> get(Long pasien);

	List<Pembayaran> get(Date awal, Date akhir);

}
