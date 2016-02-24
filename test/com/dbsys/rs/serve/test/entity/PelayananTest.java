package com.dbsys.rs.serve.test.entity;

import static org.junit.Assert.*;

import java.time.Month;

import org.junit.Test;

import com.dbsys.rs.DateUtil;
import com.dbsys.rs.Kelas;
import com.dbsys.rs.Penanggung;
import com.dbsys.rs.serve.entity.Pasien;
import com.dbsys.rs.serve.entity.Pelayanan;
import com.dbsys.rs.serve.entity.PelayananTemporal;
import com.dbsys.rs.serve.entity.Tindakan;
import com.dbsys.rs.serve.entity.Unit;
import com.dbsys.rs.serve.entity.Pasien.Pendaftaran;
import com.dbsys.rs.serve.entity.Pasien.Perawatan;
import com.dbsys.rs.serve.entity.Pasien.StatusPasien;
import com.dbsys.rs.serve.entity.Tagihan.StatusTagihan;
import com.dbsys.rs.serve.entity.Tindakan.SatuanTindakan;

public class PelayananTest {

	@Test
	public void testTagihan_Umum() {
		long tarifTindakan = 100000L;
		long biayaTambahan = 10000L;
		int jumlahTindakan = 1;

		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.I);
		tindakan.setKeterangan(null);
		tindakan.setKode("010001");
		tindakan.setNama("Visite");
		tindakan.setSatuan(SatuanTindakan.TINDAKAN);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(tarifTindakan);
		
		Pasien pasien = new Pasien();
		pasien.setPenanggung(Penanggung.UMUM);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_JALAN);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);

		Unit unit = new Unit();
		unit.setNama("Ruang Anggrek");
		unit.setTipe(Unit.TipeUnit.RUANG_PERAWATAN);
		unit.setBobot(1f);

		Pelayanan pelayanan = new Pelayanan();
		pelayanan.setUnit(unit);
		pelayanan.setTindakan(tindakan);
		pelayanan.setPasien(pasien);
		pelayanan.setBiayaTambahan(biayaTambahan);
		pelayanan.setJumlah(jumlahTindakan);
		pelayanan.setKeterangan("Biaya Administrasi");
		pelayanan.setTanggal(DateUtil.getDate());
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		assertEquals(new Long(110000), pelayanan.getTagihan());
		assertEquals(new Long(110000), pelayanan.getTagihanCounted());
	}

	@Test
	public void testTagihan_Bpjs() {
		long tarifTindakan = 100000L;
		long biayaTambahan = 10000L;
		int jumlahTindakan = 1;

		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.I);
		tindakan.setKeterangan(null);
		tindakan.setKode("010001");
		tindakan.setNama("Visite");
		tindakan.setSatuan(SatuanTindakan.TINDAKAN);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(tarifTindakan);
		
		Pasien pasien = new Pasien();
		pasien.setPenanggung(Penanggung.BPJS);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_JALAN);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);

		Unit unit = new Unit();
		unit.setNama("Ruang Anggrek");
		unit.setTipe(Unit.TipeUnit.RUANG_PERAWATAN);
		unit.setBobot(1f);

		Pelayanan pelayanan = new Pelayanan();
		pelayanan.setUnit(unit);
		pelayanan.setTindakan(tindakan);
		pelayanan.setPasien(pasien);
		pelayanan.setBiayaTambahan(biayaTambahan);
		pelayanan.setJumlah(jumlahTindakan);
		pelayanan.setKeterangan("Biaya Administrasi");
		pelayanan.setTanggal(DateUtil.getDate());
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		assertEquals(new Long(110000), pelayanan.getTagihan());
		assertEquals(new Long(0), pelayanan.getTagihanCounted());
	}

	@Test
	public void testTagihanIcu_Umum() {
		long tarifTindakan = 100000L;
		long biayaTambahan = 10000L;
		int jumlahTindakan = 1;

		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.I);
		tindakan.setKeterangan(null);
		tindakan.setKode("010001");
		tindakan.setNama("Visite");
		tindakan.setSatuan(SatuanTindakan.TINDAKAN);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(tarifTindakan);
		
		Pasien pasien = new Pasien();
		pasien.setPenanggung(Penanggung.UMUM);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_JALAN);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);

		Unit unit = new Unit();
		unit.setNama("ICU");
		unit.setTipe(Unit.TipeUnit.ICU);
		unit.setBobot(1f);

		Pelayanan pelayanan = new Pelayanan();
		pelayanan.setUnit(unit);
		pelayanan.setTindakan(tindakan);
		pelayanan.setPasien(pasien);
		pelayanan.setBiayaTambahan(biayaTambahan);
		pelayanan.setJumlah(jumlahTindakan);
		pelayanan.setKeterangan("Biaya Administrasi");
		pelayanan.setTanggal(DateUtil.getDate());
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		assertEquals(new Long(210000), pelayanan.getTagihan());
		assertEquals(new Long(210000), pelayanan.getTagihanCounted());
	}

	@Test
	public void testTagihanIcu_Bpjs() {
		long tarifTindakan = 100000L;
		long biayaTambahan = 10000L;
		int jumlahTindakan = 1;

		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.I);
		tindakan.setKeterangan(null);
		tindakan.setKode("010001");
		tindakan.setNama("Visite");
		tindakan.setSatuan(SatuanTindakan.TINDAKAN);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(tarifTindakan);
		
		Pasien pasien = new Pasien();
		pasien.setPenanggung(Penanggung.BPJS);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_JALAN);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);

		Unit unit = new Unit();
		unit.setNama("ICU");
		unit.setTipe(Unit.TipeUnit.ICU);
		unit.setBobot(1f);

		Pelayanan pelayanan = new Pelayanan();
		pelayanan.setUnit(unit);
		pelayanan.setTindakan(tindakan);
		pelayanan.setPasien(pasien);
		pelayanan.setBiayaTambahan(biayaTambahan);
		pelayanan.setJumlah(jumlahTindakan);
		pelayanan.setKeterangan("Biaya Administrasi");
		pelayanan.setTanggal(DateUtil.getDate());
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		assertEquals(new Long(210000), pelayanan.getTagihan());
		assertEquals(new Long(0), pelayanan.getTagihanCounted());
	}

	@Test
	public void testTagihanRawatInapIcu_Umum() {
		long tarifTindakan = 100000L;
		long biayaTambahan = 10000L;
		int jumlahTindakan = 1;

		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.ICU);
		tindakan.setKeterangan(null);
		tindakan.setKode("010001");
		tindakan.setNama("Rawat Inap ICU (Kelas 1)");
		tindakan.setSatuan(SatuanTindakan.HARI);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(tarifTindakan);
		
		Pasien pasien = new Pasien();
		pasien.setPenanggung(Penanggung.UMUM);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_INAP);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);

		Unit unit = new Unit();
		unit.setNama("ICU");
		unit.setTipe(Unit.TipeUnit.ICU);
		unit.setBobot(1f);

		PelayananTemporal pelayanan = new PelayananTemporal();
		pelayanan.setUnit(unit);
		pelayanan.setTindakan(tindakan);
		pelayanan.setPasien(pasien);
		pelayanan.setBiayaTambahan(biayaTambahan);
		pelayanan.setJumlah(jumlahTindakan);
		pelayanan.setKeterangan("Biaya Administrasi");
		pelayanan.setTanggalMulai(DateUtil.getDate(2015, Month.NOVEMBER, 1));
		pelayanan.setTanggalSelesai(DateUtil.getDate(2015, Month.NOVEMBER, 2));
		pelayanan.setJamMasuk(DateUtil.getTime(12, 0, 0));
		pelayanan.setJamKeluar(DateUtil.getTime(12, 0, 0));
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		assertEquals(new Long(210000), pelayanan.getTagihan());
		assertEquals(new Long(210000), pelayanan.getTagihanCounted());
	}

	@Test
	public void testTagihanRawatInapIcu_Bpjs() {
		long tarifTindakan = 100000L;
		long biayaTambahan = 10000L;
		int jumlahTindakan = 1;

		Tindakan tindakan = new Tindakan();
		tindakan.setKelas(Kelas.ICU);
		tindakan.setKeterangan(null);
		tindakan.setKode("010001");
		tindakan.setNama("Rawat Inap ICU (Kelas 1)");
		tindakan.setSatuan(SatuanTindakan.HARI);
		tindakan.setPenanggung(Penanggung.BPJS);
		tindakan.setTarif(tarifTindakan);
		
		Pasien pasien = new Pasien();
		pasien.setPenanggung(Penanggung.BPJS);
		pasien.setStatus(StatusPasien.PERAWATAN);
		pasien.setTipePerawatan(Perawatan.RAWAT_INAP);
		pasien.setTanggalMasuk(DateUtil.getDate());
		pasien.setPendaftaran(Pendaftaran.LOKET);

		Unit unit = new Unit();
		unit.setNama("ICU");
		unit.setTipe(Unit.TipeUnit.ICU);
		unit.setBobot(1f);

		PelayananTemporal pelayanan = new PelayananTemporal();
		pelayanan.setUnit(unit);
		pelayanan.setTindakan(tindakan);
		pelayanan.setPasien(pasien);
		pelayanan.setBiayaTambahan(biayaTambahan);
		pelayanan.setJumlah(jumlahTindakan);
		pelayanan.setKeterangan("Biaya Administrasi");
		pelayanan.setTanggalMulai(DateUtil.getDate(2015, Month.NOVEMBER, 1));
		pelayanan.setTanggalSelesai(DateUtil.getDate(2015, Month.NOVEMBER, 3));
		pelayanan.setJamMasuk(DateUtil.getTime(12, 0, 0));
		pelayanan.setJamKeluar(DateUtil.getTime(12, 0, 0));
		pelayanan.setStatus(StatusTagihan.MENUNGGAK);

		assertEquals(new Long(210000), pelayanan.getTagihan());
		assertEquals(new Long(0), pelayanan.getTagihanCounted());
	}
}
