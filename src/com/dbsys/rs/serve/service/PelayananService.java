package com.dbsys.rs.serve.service;

import java.util.List;

import com.dbsys.rs.lib.entity.Pelayanan;

/**
 * Interface untuk memproses pelayanan tindakan terhadap pasien.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public interface PelayananService {

	/**
	 * Menyimpan pelayanan tindakan terhadap pasien.
	 * 
	 * @param pelayanan
	 * 
	 * @return pelayanan yang sudah tersimpan
	 */
	Pelayanan simpan(Pelayanan pelayanan);

	/**
	 * Mengambil pelayanan tindakan terhadap pasien menurut {@code id}.
	 * 
	 * @param id
	 * 
	 * @return pelayanan tindakan terhadap pasien
	 */
	Pelayanan getById(Long id);

	/**
	 * Mengambil semua pelayanan tindakan yang diberikan terhadap pasien.
	 * 
	 * @param id idPasien
	 * 
	 * @return daftar pelayanan tindakan
	 */
	List<Pelayanan> getByPasien(Long id);

}
