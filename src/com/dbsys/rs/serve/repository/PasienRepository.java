package com.dbsys.rs.serve.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pasien.Perawatan;
import com.dbsys.rs.lib.entity.PelayananTemporal;

public interface PasienRepository extends JpaRepository<Pasien, Long> {

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Pasien p SET p.tipePerawatan = :tipe, p.perawatan = :perawatan, p.tanggalRawatInap = :tanggal WHERE p.id = :id")
	void convert(@Param("id") Long id, @Param("tipe") Perawatan tipe, @Param("perawatan") PelayananTemporal perawatan, @Param("tanggal") Date tanggalRawatInap);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Pasien p SET p.perawatan = :perawatan WHERE p.id = :id")
	void keluar(@Param("id") Long id, @Param("perawatan") PelayananTemporal perawatan);
	
}
