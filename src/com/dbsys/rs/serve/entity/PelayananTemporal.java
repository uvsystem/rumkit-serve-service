package com.dbsys.rs.serve.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.dbsys.rs.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("TEMPORAL")
public class PelayananTemporal extends Pelayanan {

	private Date tanggalSelesai;
	private Time jamMasuk;
	private Time jamKeluar;

	public PelayananTemporal() {
		super("TEMPORAL");
	}
	
	@Column(name = "tanggal_selesai")
	public Date getTanggalSelesai() {
		return tanggalSelesai;
	}

	public void setTanggalSelesai(Date tanggalSelesai) {
		this.tanggalSelesai = tanggalSelesai;
	}

	@Column(name = "jam_masuk")
	public Time getJamMasuk() {
		return jamMasuk;
	}

	public void setJamMasuk(Time jamMasuk) {
		this.jamMasuk = jamMasuk;
	}

	@Column(name = "jam_keluar")
	public Time getJamKeluar() {
		return jamKeluar;
	}

	public void setJamKeluar(Time jamKeluar) {
		this.jamKeluar = jamKeluar;
	}

	/**
	 * Wrapper untuk variabel tanggal pada superclass.
	 * 
	 * @return tanggal mulai pelayanan
	 */
	@Transient
	public Date getTanggalMulai() {
		return super.getTanggal();
	}
	
	public void setTanggalMulai(Date tanggalMulai) {
		super.setTanggal(tanggalMulai);
	}
	
	@Override
	@Transient
	public Long getTagihan() {
		if (tindakan.getSatuan().equals(Tindakan.SatuanTindakan.HARI)) {
			hitungJumlahHari();
		} else if(tindakan.getSatuan().equals(Tindakan.SatuanTindakan.JAM)) {
			hitungJumlahJam();
		}
		
		return super.getTagihan();
	}

	public Integer hitungJumlahHari() {
		if (jumlah == null || jumlah == 0) {
			long jumlahJam = hitungJumlahJam();
			long hasil = jumlahJam / 24;
			
			if (jumlahJam % 24 > 0)
				hasil++;
			
			jumlah = (int)hasil;
		}
		
		return jumlah;
	}
	
	public Integer hitungJumlahJam() {
		if (jumlah == null || jumlah == 0) {
			Date tanggalHitung = tanggalSelesai;
			Time jamHitung = jamKeluar;
			
			if (tanggalHitung == null)
				tanggalHitung = DateUtil.getDate();
			if (jamHitung == null)
				jamHitung = DateUtil.getTime();

			jumlah = getJumlahJam(tanggal, tanggalHitung, jamMasuk, jamHitung);
		}

		return jumlah;
	}
	
	@JsonIgnore
	@Transient
	public Integer getJumlahJam() {
		return getJumlahJam(tanggal, tanggalSelesai, jamMasuk, jamKeluar);
	}
	
	@JsonIgnore
	@Transient
	public Integer getJumlahJam(Date dateAwal, Date dateAkhir, Time timeAwal, Time timeAkhir) {
		int hari = DateUtil.calculate(dateAwal, dateAkhir);
		int jam = DateUtil.calculate(timeAwal, timeAkhir);
		
		return jam + (hari * 24);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((jamKeluar == null) ? 0 : jamKeluar.hashCode());
		result = prime * result
				+ ((jamMasuk == null) ? 0 : jamMasuk.hashCode());
		result = prime * result
				+ ((tanggalSelesai == null) ? 0 : tanggalSelesai.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PelayananTemporal other = (PelayananTemporal) obj;
		if (jamKeluar == null) {
			if (other.jamKeluar != null)
				return false;
		} else if (!jamKeluar.equals(other.jamKeluar))
			return false;
		if (jamMasuk == null) {
			if (other.jamMasuk != null)
				return false;
		} else if (!jamMasuk.equals(other.jamMasuk))
			return false;
		if (tanggalSelesai == null) {
			if (other.tanggalSelesai != null)
				return false;
		} else if (!tanggalSelesai.equals(other.tanggalSelesai))
			return false;
		return true;
	}
}
