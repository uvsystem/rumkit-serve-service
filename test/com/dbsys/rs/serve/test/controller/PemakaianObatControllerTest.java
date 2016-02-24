package com.dbsys.rs.serve.test.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.dbsys.rs.DateUtil;
import com.dbsys.rs.NumberException;
import com.dbsys.rs.serve.PasienOutException;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.entity.Barang;
import com.dbsys.rs.serve.entity.ObatFarmasi;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pasien.Pendaftaran;
import com.dbsys.rs.serve.entity.Unit;
import com.dbsys.rs.serve.entity.Pasien.Perawatan;
import com.dbsys.rs.serve.entity.Pemakaian;
import com.dbsys.rs.serve.entity.Penduduk;
import com.dbsys.rs.serve.entity.Pasien.StatusPasien;
import com.dbsys.rs.serve.entity.Penduduk.Kelamin;
import com.dbsys.rs.serve.repository.BarangRepository;
import com.dbsys.rs.serve.repository.PasienRepository;
import com.dbsys.rs.serve.repository.PemakaianRepository;
import com.dbsys.rs.serve.service.PemakaianService;
import com.dbsys.rs.serve.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PemakaianObatControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private PemakaianService pemakaianService;
	@Autowired
	private PemakaianRepository pemakaianRepository;
	@Autowired
	private PasienRepository pasienRepository;
	@Autowired
	private BarangRepository barangRepository;

	private Pemakaian pemakaian;
	
	@Before
	public void setup() throws NumberException, PasienOutException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = pemakaianRepository.count();
		
		Barang barang = new ObatFarmasi("Keterangan");
		barang.setHarga(20000l);
		barang.setJumlah(20l);
		barang.setKode("OBxxx");
		barang.setNama("Obat xxx");
		barang.setSatuan("Satuan");
		barang.setPenanggung(Penanggung.BPJS);
		barang = barangRepository.save(barang);
		
		Penduduk penduduk = new Penduduk();
		penduduk.setAgama("Kristen");
		penduduk.setDarah("O");
		penduduk.setKelamin(Kelamin.PRIA);
		penduduk.setNama("Penduduk xxx");
		penduduk.setNik("Nik xxx");
		penduduk.setTanggalLahir(DateUtil.getDate());
		penduduk.setTelepon("Telepon");
		penduduk.generateKode();

		Pasien pasien = new Pasien();
		pasien.setPenduduk(penduduk);
		pasien.setPenanggung(Penanggung.BPJS);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_JALAN);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);
		pasien.setKode("PSN Blum Ada");
		pasien = pasienRepository.save(pasien);
		
		Unit unit = new Unit();
		unit.setNama("Nama Unit xxxxxxxx");
		unit.setTipe(Unit.TipeUnit.APOTEK_FARMASI);
		unit.setBobot(1f);

		pemakaian = new Pemakaian();
		pemakaian.setBarang(barang);
		pemakaian.setPasien(pasien);
		pemakaian.setBiayaTambahan(10000L);
		pemakaian.setUnit(unit);
		pemakaian.setJumlah(2);
		pemakaian.setKeterangan("Biaya Administrasi");
		pemakaian.setTanggal(DateUtil.getDate());
		pemakaian.setNomorResep("Nomor Resep");
		pemakaian = pemakaianService.simpan(pemakaian);

		assertEquals(count + 1, pemakaianRepository.count());
		assertEquals(new Long(50000), pemakaian.getTagihan());
	}
	
	@Test
	public void testSimpan() throws Exception {
		this.mockMvc.perform(
				post("/pemakaian")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"barang\": {"
						+ "\"id\": \"" + pemakaian.getBarang().getId() + "\","
						+ "\"harga\": \"20000\","
						+ "\"jumlah\": \"100\","
						+ "\"kode\": \"OBxxxx\","
						+ "\"nama\":\"Obat xxxx\","
						+ "\"satuan\":\"satuan\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"tipeBarang\": \"OBAT\""
						+ "},"
						+ "\"pasien\": {"
						+ "\"id\": \"" + pemakaian.getPasien().getId() + "\","
						+ "\"penduduk\": {"
						+ "\"agama\": \"Kristen\","
						+ "\"darah\": \"O\","
						+ "\"kelamin\": \"PRIA\","
						+ "\"nama\":\"Penduduk xxxx\","
						+ "\"nik\":\"nik xxxx\","
						+ "\"tanggalLahir\":\"1991-12-05\","
						+ "\"telepon\":\"telepon 2\","
						+ "\"kode\": \"KODE\""
						+ "},"
						+ "\"penanggung\": \"BPJS\","
						+ "\"status\": \"PERAWATAN\","
						+ "\"tipePerawatan\": \"RAWAT_JALAN\","
						+ "\"tanggalMasuk\": \"2015-10-1\","
						+ "\"kode\": \"KODE\""
						+ "},"
						+ "\"unit\":{"
						+ "\"nama\": \"Unit xxxxxxxxx\","
						+ "\"tipe\": \"APOTEK_FARMASI\","
						+ "\"bobot\": \"1\""
						+ "},"
						+ "\"biayaTambahan\":\"10000\","
						+ "\"jumlah\":\"2\","
						+ "\"keterangan\":\"Keterangan\","
						+ "\"tanggal\":\"2015-10-14\","
						+ "\"nomorResep\": \"010101\""
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tagihan").value(50000))
			.andExpect(jsonPath("$.message").value("Berhasil"));

		assertEquals(count + 2, pemakaianRepository.count());
	}

	@Test
	public void testGetById() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pemakaian/%d", pemakaian.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetByPasien() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pemakaian/pasien/%d", pemakaian.getPasien().getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetByNomorResep() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pemakaian/nomor/%s", pemakaian.getNomorResep()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
