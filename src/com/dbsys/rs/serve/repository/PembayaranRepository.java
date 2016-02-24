package com.dbsys.rs.serve.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbsys.rs.serve.entity.Pembayaran;

public interface PembayaranRepository extends JpaRepository<Pembayaran, String> {

	List<Pembayaran> findByPasien_Id(Long pasien);

	List<Pembayaran> findByTanggalBetween(Date awal, Date akhir);

}
