package com.dbsys.rs.serve.controller;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.RestMessage;
import com.dbsys.rs.lib.entity.PelayananTemporal;
import com.dbsys.rs.serve.service.PelayananService;

@Controller
@RequestMapping("/pelayanan/temporal")
public class PelayananRuanganController {
	
	@Autowired
	private PelayananService pelayananService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public RestMessage masuk(@RequestBody PelayananTemporal pelayanan) throws ApplicationException, PersistenceException {
		pelayananService.masuk(pelayanan);

		return RestMessage.success();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/tanggal/{tanggal}/jam/{jam}/tambahan/{tambahan}/keterangan/{keterangan}")
	@ResponseBody
	public RestMessage keluar(@PathVariable Long id, @PathVariable Date tanggal, @PathVariable Time jam, @PathVariable Long tambahan, @PathVariable String keterangan) throws ApplicationException, PersistenceException {
		pelayananService.keluar(id, tanggal, jam, tambahan, keterangan);
		return RestMessage.success();
	}
}
