package com.dbsys.rs.serve.test.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.Kelas;
import com.dbsys.rs.lib.Penanggung;
import com.dbsys.rs.lib.entity.Dokter;
import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pasien.Pendaftaran;
import com.dbsys.rs.lib.entity.Pegawai;
import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.lib.entity.Penduduk;
import com.dbsys.rs.lib.entity.Dokter.Spesialisasi;
import com.dbsys.rs.lib.entity.Pasien.StatusPasien;
import com.dbsys.rs.lib.entity.Pasien.Perawatan;
import com.dbsys.rs.lib.entity.Penduduk.Kelamin;
import com.dbsys.rs.lib.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.lib.entity.Tindakan.SatuanTindakan;
import com.dbsys.rs.lib.entity.Tindakan;
import com.dbsys.rs.lib.entity.Unit;
import com.dbsys.rs.serve.repository.PasienRepository;
import com.dbsys.rs.serve.repository.PelayananRepository;
import com.dbsys.rs.serve.service.PelayananService;
import com.dbsys.rs.serve.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PelayananControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	private long count;

	@Autowired
	private PelayananService pelayananService;
	@Autowired
	private PelayananRepository pelayananRepository;
	@Autowired
	private PasienRepository pasienRepository;

	private Pelayanan pelayanan;
	private Pasien pasien;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		count = pelayananRepository.count();
		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.I);
		tindakan.setKeterangan(null);
		tindakan.setKode("TDKxxxxxxxx");
		tindakan.setNama("Nama Tindakan xxxxxxx");
		tindakan.setSatuan(SatuanTindakan.TINDAKAN);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(20000l);
		
		Penduduk penduduk = new Penduduk();
		penduduk.setAgama("Kristen");
		penduduk.setDarah("O");
		penduduk.setKelamin(Kelamin.PRIA);
		penduduk.setNama("Penduduk xxxxxxx");
		penduduk.setNik("Nik xxxxxxx");
		penduduk.setTanggalLahir(DateUtil.getDate());
		penduduk.setTelepon("Telepon");
		penduduk.generateKode();
		
		Unit unit = new Unit();
		unit.setNama("Nama Unit xxxxxxxxx");
		unit.setTipe(Unit.TipeUnit.APOTEK_FARMASI);
		unit.setBobot(1f);

		pasien = new Pasien();
		String kode = pasien.generateKode();

		pasien.setPenduduk(penduduk);
		pasien.setPenanggung(Penanggung.BPJS);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_JALAN);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);
		pasien.setTujuan(unit);
		pasien.setKode(kode);
		pasien = pasienRepository.save(pasien);
		
		Penduduk penduduk2 = new Penduduk();
		penduduk2.setAgama("Kristen");
		penduduk2.setDarah("O");
		penduduk2.setKelamin(Kelamin.PRIA);
		penduduk2.setNama("Penduduk xxxxxxxxxxxxx");
		penduduk2.setNik("Nik xxxxxxxxxxx");
		penduduk2.setTanggalLahir(DateUtil.getDate());
		penduduk2.setTelepon("Telepon");
		penduduk2.generateKode();
		
		Pegawai pegawai = new Dokter(Spesialisasi.UMUM);
		pegawai.setPenduduk(penduduk2);
		pegawai.setNip("Nip xxxxxxxx");
		
		pelayanan = new Pelayanan();
		pelayanan.setTindakan(tindakan);
		pelayanan.setPasien(pasien);
		pelayanan.setPelaksana(pegawai);
		pelayanan.setUnit(unit);
		pelayanan.setBiayaTambahan(10000L);
		pelayanan.setJumlah(2);
		pelayanan.setKeterangan("Biaya Administrasi");
		pelayanan.setTanggal(DateUtil.getDate());
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);
		pelayanan = pelayananService.simpan(pelayanan);

		assertEquals(count + 1, pelayananRepository.count());
		assertEquals(new Long(50000), pelayanan.getTagihan());
	}
	
	@Test
	public void testSimpan() throws Exception {
		this.mockMvc.perform(
				post("/pelayanan")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"tipePelayanan\": \"PELAYANAN\","
						+ "\"tindakan\": {"
						+ "\"kelas\": \"I\","
						+ "\"keterangan\": \"-\","
						+ "\"kode\":\"TDK xxxx\","
						+ "\"nama\": \"Nama asasasasasa\","
						+ "\"satuan\":\"TINDAKAN\","
						+ "\"penanggung\":\"UMUM\","
						+ "\"tarif\":\"100000\""
						+ "},"
						+ "\"pasien\": {"
						+ "\"id\": \"" + pasien.getId() + "\","
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
						+ "\"pelaksana\": {"
						+ "\"penduduk\": {"
						+ "\"agama\": \"Kristen\","
						+ "\"darah\": \"O\","
						+ "\"kelamin\": \"PRIA\","
						+ "\"nama\":\"Penduduk xxx-xxx-xxx-xxx\","
						+ "\"nik\":\"nik xxxx-xxxx-xxxx-xxxx\","
						+ "\"tanggalLahir\":\"1991-12-05\","
						+ "\"telepon\":\"telepon 2\","
						+ "\"kode\": \"KODE xxx-xxx-xxx\""
						+ "},"
						+ "\"nip\":\"nip xxxxx\","
						+ "\"tipePegawai\": \"DOKTER\""
						+ "},"
						+ "\"unit\":{"
						+ "\"nama\": \"Unit xxxxxxxxx\","
						+ "\"tipe\": \"APOTEK_FARMASI\","
						+ "\"bobot\": \"1\""
						+ "},"
						+ "\"biayaTambahan\":\"0\","
						+ "\"jumlah\":\"1\","
						+ "\"keterangan\":\"Keterangan\","
						+ "\"tanggal\":\"2015-10-14\","
						+ "\"status\": \"MENUNGGAK\""
						+ "}")
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.tagihan").value(100000))
			.andExpect(jsonPath("$.model.tipe").value("PELAYANAN"))
			.andExpect(jsonPath("$.model.pasien.totalTagihan").value(100000))
			.andExpect(jsonPath("$.message").value("Berhasil"));

		assertEquals(count + 2, pelayananRepository.count());
	}

	@Test
	public void testHapus() throws Exception {
		this.mockMvc.perform(
				delete(String.format("/pelayanan/%d", pelayanan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.model.totalTagihan").value(0))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetById() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pelayanan/%d", pelayanan.getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("ENTITY"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}

	@Test
	public void testGetByPasien() throws Exception {
		this.mockMvc.perform(
				get(String.format("/pelayanan/pasien/%d", pelayanan.getPasien().getId()))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(jsonPath("$.tipe").value("LIST"))
			.andExpect(jsonPath("$.message").value("Berhasil"));
	}
}
