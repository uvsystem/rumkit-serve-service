package com.dbsys.rs.serve.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.dbsys.rs.NumberException;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.PasienOutException;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pelayanan;
import com.dbsys.rs.serve.entity.PelayananTemporal;

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
	 * 
	 * @throws PasienOutException proses melibatkan pasien yang sudah keluar
	 * @throws NumberException jumlah tindakan <= 0. 
	 */
	Pelayanan simpan(Pelayanan pelayanan) throws PasienOutException, NumberException;

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

	List<Pelayanan> get(Date awal, Date akhir, Penanggung penanggung);

	Pelayanan keluar(Long id, Date tanggal, Time jam, Long tambahan, String keterangan);

	void masuk(PelayananTemporal pelayanan) throws PasienOutException;

	Pasien hapus(Long id);

}
