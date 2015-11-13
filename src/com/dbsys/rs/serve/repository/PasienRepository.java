package com.dbsys.rs.serve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.lib.Kelas;
import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pasien.Perawatan;
import com.dbsys.rs.lib.entity.PelayananTemporal;

public interface PasienRepository extends JpaRepository<Pasien, Long> {

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Pasien p SET p.kelas = :kelas, p.tipePerawatan = :tipe, p.perawatan = :perawatan WHERE p.id = :id")
	void convert(@Param("id") Long id, @Param("kelas") Kelas kelas, @Param("tipe") Perawatan tipe, @Param("perawatan") PelayananTemporal perawatan);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Pasien p SET p.perawatan = :perawatan WHERE p.id = :id")
	void keluar(@Param("id") Long id, @Param("perawatan") PelayananTemporal perawatan);
	
}
