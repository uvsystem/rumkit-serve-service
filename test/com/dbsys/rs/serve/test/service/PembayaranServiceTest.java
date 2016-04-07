package com.dbsys.rs.serve.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.ApplicationException;
import com.dbsys.rs.DateUtil;
import com.dbsys.rs.Kelas;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.entity.Dokter;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pegawai;
import com.dbsys.rs.serve.entity.Pelayanan;
import com.dbsys.rs.serve.entity.Pembayaran;
import com.dbsys.rs.serve.entity.Penduduk;
import com.dbsys.rs.serve.entity.Tindakan;
import com.dbsys.rs.serve.entity.Unit;
import com.dbsys.rs.serve.entity.Dokter.Spesialisasi;
import com.dbsys.rs.serve.entity.Pasien.Pendaftaran;
import com.dbsys.rs.serve.entity.Pasien.Perawatan;
import com.dbsys.rs.serve.entity.Pasien.StatusPasien;
import com.dbsys.rs.serve.entity.Penduduk.Kelamin;
import com.dbsys.rs.serve.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.serve.entity.Tindakan.SatuanTindakan;
import com.dbsys.rs.serve.repository.PasienRepository;
import com.dbsys.rs.serve.repository.PembayaranRepository;
import com.dbsys.rs.serve.service.PelayananService;
import com.dbsys.rs.serve.service.PembayaranService;
import com.dbsys.rs.serve.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PembayaranServiceTest {

	@Autowired
	private PelayananService pelayananService;
	@Autowired
	private PembayaranService pembayaranService;

	@Autowired
	private PasienRepository pasienRepository;
	@Autowired
	private PembayaranRepository pembayaranRepository;

	@Test
	public void testInsert() throws ApplicationException {
		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.I);
		tindakan.setKeterangan(null);
		tindakan.setKode("TDKxxxxxxxxxxxxxxxxx");
		tindakan.setNama("Nama Tindakan xxxxxxx");
		tindakan.setSatuan(SatuanTindakan.TINDAKAN);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(20000l);
		
		Penduduk penduduk = new Penduduk();
		penduduk.setKode("kode xxxxxxxxxxxxx");
		penduduk.setAgama("Kristen");
		penduduk.setDarah("O");
		penduduk.setKelamin(Kelamin.PRIA);
		penduduk.setNama("Penduduk xxxxxxxxxxxxx");
		penduduk.setNik("Nik xxxxxxxxxxxxx");
		penduduk.setTanggalLahir(DateUtil.getDate());
		penduduk.setTelepon("Telepon");
		
		Unit unit = new Unit();
		unit.setNama("Nama Unit xxxxxxxxxxxxxxx");
		unit.setTipe(Unit.TipeUnit.APOTEK_FARMASI);
		unit.setBobot(1f);

		Pasien pasien = new Pasien();
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
		penduduk2.setKode("kode yyyyyyyyy");
		penduduk2.setAgama("Kristen");
		penduduk2.setDarah("O");
		penduduk2.setKelamin(Kelamin.PRIA);
		penduduk2.setNama("Penduduk yyyyyyyyyyyyyy");
		penduduk2.setNik("Nik yyyyyyyyyyyyyy");
		penduduk2.setTanggalLahir(DateUtil.getDate());
		penduduk2.setTelepon("Telepon");
		
		Pegawai pegawai = new Dokter(Spesialisasi.UMUM);
		pegawai.setPenduduk(penduduk2);
		pegawai.setNip("Nip xxxxxxxxxxxxxx");
		
		Pelayanan pelayanan = new Pelayanan();
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
		
		assertEquals(new Long(50000), pelayanan.getTagihan());
		
		List<Pelayanan> list = new ArrayList<>();
		list.add(pelayanan);

		long countPembayaran = pembayaranRepository.count();
		
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setKode("BYR xxxxx");
		pembayaran.setJumlah(50000L);
		pembayaran.setPasien(pasien);
		pembayaran.setListPelayanan(list);
		pembayaran = pembayaranService.simpan(pembayaran);
		
		assertNotNull(pembayaran.getTanggal());
		assertNotNull(pembayaran.getJam());
		assertEquals(countPembayaran + 1, pembayaranRepository.count());
	}

	@Test
	public void testKurang() throws ApplicationException {
		Penduduk penduduk = new Penduduk();
		penduduk.setKode("kode xxxxxxxxxxxxx");
		penduduk.setAgama("Kristen");
		penduduk.setDarah("O");
		penduduk.setKelamin(Kelamin.PRIA);
		penduduk.setNama("Penduduk xxxxxxxxxxxxx");
		penduduk.setNik("Nik xxxxxxxxxxxxx");
		penduduk.setTanggalLahir(DateUtil.getDate());
		penduduk.setTelepon("Telepon");
		
		Unit unit = new Unit();
		unit.setNama("Nama Unit xxxxxxxxxxxxxxx");
		unit.setTipe(Unit.TipeUnit.APOTEK_FARMASI);
		unit.setBobot(1f);

		Pasien pasien = new Pasien();
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
		penduduk2.setKode("kode yyyyyyyyy");
		penduduk2.setAgama("Kristen");
		penduduk2.setDarah("O");
		penduduk2.setKelamin(Kelamin.PRIA);
		penduduk2.setNama("Penduduk yyyyyyyyyyyyyy");
		penduduk2.setNik("Nik yyyyyyyyyyyyyy");
		penduduk2.setTanggalLahir(DateUtil.getDate());
		penduduk2.setTelepon("Telepon");

		long countPembayaran = pembayaranRepository.count();
		
		Pembayaran pembayaran = new Pembayaran();
		pembayaran.setKode("BYR xxxxx");
		pembayaran.setJumlah(-50000L);
		pembayaran.setPasien(pasien);
		pembayaran = pembayaranService.simpan(pembayaran);
		
		assertNotNull(pembayaran.getTanggal());
		assertNotNull(pembayaran.getJam());
		assertEquals(new Long(-50000), pembayaran.getJumlah());
		assertEquals(new Long(-50000), pasien.getCicilan());
		assertEquals(countPembayaran + 1, pembayaranRepository.count());
	}
}
