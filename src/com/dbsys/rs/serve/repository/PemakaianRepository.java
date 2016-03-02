package com.dbsys.rs.serve.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.entity.Pemakaian;

public interface PemakaianRepository extends JpaRepository<Pemakaian, Long> {

	List<Pemakaian> findByPasien_Id(Long id);

	List<Pemakaian> findByNomorResep(String nomorResep);

	List<Pemakaian> findByTanggalBetweenAndPasien_PenanggungAndBarang_PenanggungOrderByPasien_KodeAsc(Date awal, Date akhir, 
			Penanggung penanggungPasien, Penanggung penanggungBarang);

	List<Pemakaian> findByTanggalBetweenAndPasien_PenanggungOrBarang_PenanggungOrderByPasien_KodeAsc(Date awal, Date akhir, 
			Penanggung penanggungPasien, Penanggung penanggungBarang);

}
