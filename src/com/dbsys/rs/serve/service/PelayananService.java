package com.dbsys.rs.serve.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.lib.entity.PelayananTemporal;

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

	Pelayanan keluar(Long id, Date tanggal, Time jam, Long tambahan, String keterangan);

	void masuk(PelayananTemporal pelayanan);

	Pasien hapus(Long id);

}
