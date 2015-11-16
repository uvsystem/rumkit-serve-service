package com.dbsys.rs.serve.controller;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.EntityRestMessage;
import com.dbsys.rs.lib.ListEntityRestMessage;
import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.serve.service.PelayananService;

@Controller
@RequestMapping("/pelayanan")
public class PelayananController {
	
	@Autowired
	private PelayananService pelayananService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Pelayanan> simpan(@RequestBody Pelayanan pelayanan) throws ApplicationException, PersistenceException {
		pelayanan = pelayananService.simpan(pelayanan);
		return EntityRestMessage.createPelayanan(pelayanan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<Pelayanan> getById(@PathVariable Long id) throws ApplicationException, PersistenceException {
		Pelayanan pelayanan = (Pelayanan)pelayananService.getById(id);
		return EntityRestMessage.createPelayanan(pelayanan);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pasien/{id}")
	@ResponseBody
	public ListEntityRestMessage<Pelayanan> getByPasien(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Pelayanan> list = pelayananService.getByPasien(id);
		return ListEntityRestMessage.createListPelayanan(list);
	}
}
