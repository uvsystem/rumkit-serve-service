package com.dbsys.rs.serve.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.serve.repository.PelayananRepository;
import com.dbsys.rs.serve.service.PelayananService;

@Service
@Transactional(readOnly = true)
public class PelayananServiceImpl implements PelayananService {

	@Autowired
	private PelayananRepository pelayananRepository;
	
	@Override
	@Transactional(readOnly = false)
	public Pelayanan simpan(Pelayanan pelayanan) {
		if (pelayanan.getTanggal() == null)
			pelayanan.setTanggal(DateUtil.getDate());
		return pelayananRepository.save(pelayanan);
	}

	@Override
	public Pelayanan getById(Long id) {
		return pelayananRepository.findOne(id);
	}

	@Override
	public List<Pelayanan> getByPasien(Long id) {
		return pelayananRepository.findByPasien_Id(id);
	}

}
