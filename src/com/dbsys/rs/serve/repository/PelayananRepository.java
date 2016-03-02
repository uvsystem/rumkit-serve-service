package com.dbsys.rs.serve.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pelayanan;

public interface PelayananRepository extends JpaRepository<Pelayanan, Long> {

	List<Pelayanan> findByPasien_Id(Long id);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE PelayananTemporal pt SET pt.tanggalSelesai = :tanggal, pt.jamKeluar = :jam, pt.biayaTambahan = :tambahan, "
			+ "pt.keterangan = :keterangan, pt.jumlah = :jumlah WHERE pt.pasien = :pasien")
	void update(@Param("pasien") Pasien pasien, @Param("tanggal") Date tanggal, @Param("jam") Time jam, 
			@Param("tambahan") Long tambahan, @Param("keterangan") String keterangan, @Param("jumlah") Integer jumlah);

	List<Pelayanan> findByTanggalBetweenAndPasien_PenanggungAndTindakan_PenanggungOrderByPasien_KodeAsc(Date awal, Date akhir,
			Penanggung penanggungPasien, Penanggung penanggungTindakan);

	List<Pelayanan> findByTanggalBetweenAndPasien_PenanggungOrTindakan_PenanggungOrderByPasien_KodeAsc(Date awal, Date akhir,
			Penanggung penanggungPasien, Penanggung penanggungTindakan);

}
