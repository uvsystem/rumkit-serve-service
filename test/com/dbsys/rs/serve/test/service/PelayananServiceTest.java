package com.dbsys.rs.serve.test.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.dbsys.rs.lib.ApplicationException;
import com.dbsys.rs.lib.DateUtil;
import com.dbsys.rs.lib.Kelas;
import com.dbsys.rs.lib.Penanggung;
import com.dbsys.rs.lib.entity.Dokter;
import com.dbsys.rs.lib.entity.Pasien;
import com.dbsys.rs.lib.entity.Pegawai;
import com.dbsys.rs.lib.entity.Pelayanan;
import com.dbsys.rs.lib.entity.Penduduk;
import com.dbsys.rs.lib.entity.Tindakan;
import com.dbsys.rs.lib.entity.Unit;
import com.dbsys.rs.lib.entity.Dokter.Spesialisasi;
import com.dbsys.rs.lib.entity.Pasien.Pendaftaran;
import com.dbsys.rs.lib.entity.Pasien.Perawatan;
import com.dbsys.rs.lib.entity.Pasien.StatusPasien;
import com.dbsys.rs.lib.entity.Penduduk.Kelamin;
import com.dbsys.rs.lib.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.lib.entity.Tindakan.SatuanTindakan;
import com.dbsys.rs.serve.repository.PasienRepository;
import com.dbsys.rs.serve.service.PelayananService;
import com.dbsys.rs.serve.test.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
@TransactionConfiguration (defaultRollback = true)
public class PelayananServiceTest {

	@Autowired
	private PelayananService pelayananService;
	@Autowired
	private PasienRepository pasienRepository;

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
	}

	@Test
	public void testUpdate() throws ApplicationException {
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
		
		pelayanan.setJumlah(10);
		pelayanan = pelayananService.simpan(pelayanan);
		
		assertEquals(new Long(210000), pelayanan.getTagihan());
	}
}
