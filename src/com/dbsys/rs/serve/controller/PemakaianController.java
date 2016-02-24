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

import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.EntityRestMessage;
import com.dbsys.rs.ListEntityRestMessage;
import com.dbsys.rs.RestMessage;
import com.dbsys.rs.serve.entity.Pemakaian;
import com.dbsys.rs.serve.service.PemakaianService;

@Controller
@RequestMapping("/pemakaian")
public class PemakaianController {

	@Autowired
	private PemakaianService pemakaianService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public EntityRestMessage<Pemakaian> simpan(@RequestBody Pemakaian pemakaian) throws ApplicationException, PersistenceException {
		pemakaian = pemakaianService.simpan(pemakaian);
		return new EntityRestMessage<Pemakaian>(pemakaian);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public RestMessage hapus(@PathVariable Long id) throws ApplicationException, PersistenceException {
		pemakaianService.hapus(id);
		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public EntityRestMessage<Pemakaian> getById(@PathVariable Long id) throws ApplicationException, PersistenceException {
		Pemakaian pemakaian = pemakaianService.getById(id);
		return new EntityRestMessage<Pemakaian>(pemakaian);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pasien/{id}")
	@ResponseBody
	public ListEntityRestMessage<Pemakaian> getByPasien(@PathVariable Long id) throws ApplicationException, PersistenceException {
		List<Pemakaian> list = pemakaianService.getByPasien(id);
		return new ListEntityRestMessage<Pemakaian>(list);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/nomor/{nomor}")
	@ResponseBody
	public ListEntityRestMessage<Pemakaian> getByNomorResep(@PathVariable String nomor) throws ApplicationException, PersistenceException {
		List<Pemakaian> list = pemakaianService.getByNomorResep(nomor);
		return new ListEntityRestMessage<Pemakaian>(list);
	}
}
