package com.dbsys.rs.serve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.lib.entity.Pelayanan;

public interface PelayananRepository extends JpaRepository<Pelayanan, Long> {

	List<Pelayanan> findByPasien_Id(Long id);

}
