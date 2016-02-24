package com.dbsys.rs.serve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dbsys.rs.serve.entity.Barang;

public interface BarangRepository extends JpaRepository<Barang, Long> {

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Barang b SET b.jumlah = :jumlah WHERE b.id = :id")
	void updateJumlah(@Param("id") Long id, @Param("jumlah") long jumlah);

}
