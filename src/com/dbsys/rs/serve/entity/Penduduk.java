
package com.dbsys.rs.serve.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dbsys.rs.CodedEntity;
import com.dbsys.rs.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "penduduk")

@JsonIgnoreProperties(ignoreUnknown = true)
public class Penduduk implements CodedEntity {

	public enum Kelamin {
		PRIA, WANITA
	}

	protected Long id;
	protected String kode;
	protected String nik;
	protected String nama;
	protected Kelamin kelamin;
	protected Date tanggalLahir;
	protected String darah;
	protected String agama;
	protected String telepon;
	
	protected String alamat;
	protected String pekerjaan;
	protected String jenisKartu;
	protected Date tanggalDaftar;
	
	public Penduduk() {
		super();
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "kode")
	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}
	
	public String generateKode() {
		String kode = createKode();
		setKode(kode);
		
		return kode;
	}
	
	public static String createKode() {
		Integer d = Math.abs(DateUtil.getDate().hashCode());
		Integer t = Math.abs(DateUtil.getTime().hashCode());

		return String.format("60%s00%s", d, t);
	}
	
	@Column(name = "nik")
	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "kelamin")
	public Kelamin getKelamin() {
		return kelamin;
	}

	public void setKelamin(Kelamin kelamin) {
		this.kelamin = kelamin;
	}

	@Column(name = "tanggal_lahir")
	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	@Column(name = "darah")
	public String getDarah() {
		return darah;
	}

	public void setDarah(String darah) {
		this.darah = darah;
	}

	@Column(name = "agama")
	public String getAgama() {
		return agama;
	}

	public void setAgama(String agama) {
		this.agama = agama;
	}

	@Column(name = "telepon")
	public String getTelepon() {
		return telepon;
	}

	public void setTelepon(String telepon) {
		this.telepon = telepon;
	}

	@Column(name = "alamat")
	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	@Column(name = "pekerjaan")
	public String getPekerjaan() {
		return pekerjaan;
	}

	public void setPekerjaan(String pekerjaan) {
		this.pekerjaan = pekerjaan;
	}

	@Column(name = "jenis_kartu")
	public String getJenisKartu() {
		return jenisKartu;
	}

	public void setJenisKartu(String jenisKartu) {
		this.jenisKartu = jenisKartu;
	}

	@Column(name = "tanggal_daftar")
	public Date getTanggalDaftar() {
		return tanggalDaftar;
	}

	public void setTanggalDaftar(Date tanggalDaftar) {
		this.tanggalDaftar = tanggalDaftar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agama == null) ? 0 : agama.hashCode());
		result = prime * result + ((darah == null) ? 0 : darah.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((kelamin == null) ? 0 : kelamin.hashCode());
		result = prime * result + ((kode == null) ? 0 : kode.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result + ((nik == null) ? 0 : nik.hashCode());
		result = prime * result
				+ ((tanggalLahir == null) ? 0 : tanggalLahir.hashCode());
		result = prime * result + ((telepon == null) ? 0 : telepon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Penduduk other = (Penduduk) obj;
		if (agama == null) {
			if (other.agama != null)
				return false;
		} else if (!agama.equals(other.agama))
			return false;
		if (darah == null) {
			if (other.darah != null)
				return false;
		} else if (!darah.equals(other.darah))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kelamin != other.kelamin)
			return false;
		if (kode == null) {
			if (other.kode != null)
				return false;
		} else if (!kode.equals(other.kode))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (nik == null) {
			if (other.nik != null)
				return false;
		} else if (!nik.equals(other.nik))
			return false;
		if (tanggalLahir == null) {
			if (other.tanggalLahir != null)
				return false;
		} else if (!tanggalLahir.equals(other.tanggalLahir))
			return false;
		if (telepon == null) {
			if (other.telepon != null)
				return false;
		} else if (!telepon.equals(other.telepon))
			return false;
		return true;
	}
}
