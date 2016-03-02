package com.dbsys.rs.serve.service;

import java.sql.Date;
import java.util.List;

import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.NumberException;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.PasienOutException;
import com.dbsys.rs.serve.entity.Pemakaian;

/**
 * Interface untuk mengelola data pemakaian.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface PemakaianService {

	/**
	 * Simpan pemakaian barang.
	 * 
	 * @param pemakaian
	 * 
	 * @return pemakaian yang berhasil disimpan
	 * @throws NumberException jumlah barang tidak mencukupi untuk dikurangi 
	 * @throws ApplicationException 
	 */
	Pemakaian simpan(Pemakaian pemakaian) throws NumberException, PasienOutException;

	/**
	 * Mengambil pemakaian barang berdasarkan id.
	 * 
	 * @param id
	 * 
	 * @return pemakaian barang
	 */
	Pemakaian getById(Long id);

	/**
	 * Mengambil daftar bhp berdasarkan pasien.
	 * 
	 * @param id
	 * 
	 * @return daftar pemakaian barang
	 */
	List<Pemakaian> getByPasien(Long id);

	List<Pemakaian> get(Date awal, Date akhir, Penanggung penanggung);

	/**
	 * Mengambil daftar pemakaian obat berdasarkan nomor resep.
	 * 
	 * @param nomor
	 * 
	 * @return daftar pemakaian obat
	 */
	List<Pemakaian> getByNomorResep(String nomorResep);

	void hapus(Long id);

}
