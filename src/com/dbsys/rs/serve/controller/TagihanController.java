package com.dbsys.rs.serve.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.ListEntityRestMessage;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.entity.Pelayanan;
import com.dbsys.rs.serve.entity.Pemakaian;
import com.dbsys.rs.serve.entity.Tagihan;
import com.dbsys.rs.serve.service.PelayananService;
import com.dbsys.rs.serve.service.PemakaianService;

@Controller
@RequestMapping("/tagihan")
public class TagihanController {

	@Autowired
	private PelayananService pelayananService;
	@Autowired
	private PemakaianService pemakaianService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/awal/{awal}/akhir/{akhir}/penanggung/{penanggung}")
	@ResponseBody
	public ListEntityRestMessage<Tagihan> get(@PathVariable Date awal, @PathVariable Date akhir, @PathVariable Penanggung penanggung) throws ApplicationException, PersistenceException {
		List<Tagihan> list = new ArrayList<>();
		List<Pelayanan> listPelayanan = pelayananService.get(awal, akhir, penanggung);
		List<Pemakaian> listPemakaian = pemakaianService.get(awal, akhir, penanggung);

		for (Tagihan tagihan : listPelayanan)
			list.add(tagihan);
		
		for (Tagihan tagihan : listPemakaian)
			list.add(tagihan);
		
		return new ListEntityRestMessage<Tagihan>(list);
	}
}
