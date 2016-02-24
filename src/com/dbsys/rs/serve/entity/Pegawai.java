package com.dbsys.rs.serve.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dbsys.rs.serve.entity.Penduduk.Kelamin;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Table(name = "pegawai")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	name = "tipe",
	discriminatorType = DiscriminatorType.STRING
)
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "tipePegawai"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Dokter.class, name = "DOKTER"),
	@JsonSubTypes.Type(value = Perawat.class, name = "PERAWAT"),
	@JsonSubTypes.Type(value = Apoteker.class, name = "APOTEKER"),
	@JsonSubTypes.Type(value = Pekerja.class, name = "PEKERJA"),
	@JsonSubTypes.Type(value = Pegawai.class, name = "PEGAWAI")
})
public class Pegawai {

	protected Long id;
	protected String nip;
	protected Penduduk penduduk;
	
	// Untuk JSON buka JPA
	private String tipePegawai;

	public Pegawai() {
		super();
		this.tipePegawai = "PEGAWAI";
		this.penduduk = new Penduduk();
	}
	
	public Pegawai(String name) {
		this();
		this.tipePegawai = name;
	}

	@Transient
	public String getTipe() {
		return tipePegawai;
	}

	public void setTipe(String tipePegawai) {
		this.tipePegawai = tipePegawai;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nip")
	public String getNip() {
		return nip;
	}
	
	public void setNip(String nip) {
		this.nip = nip;
	}

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "penduduk")
	public Penduduk getPenduduk() {
		return penduduk;
	}

	public void setPenduduk(Penduduk penduduk) {
		this.penduduk = penduduk;
	}
	
	@Transient
	public Long getIdPenduduk() {
		return penduduk.getId();
	}
	
	public void setIdPenduduk(Long idPenduduk) {
		penduduk.setId(idPenduduk);
	}

	@Transient
	public String getKode() {
		return penduduk.getKode();
	}

	public void setKode(String kode) {
		penduduk.setKode(kode);
	}

	@Transient
	public String getNik() {
		return penduduk.getNik();
	}

	public void setNik(String nik) {
		penduduk.setNik(nik);
	}

	@Transient
	public String getNama() {
		return penduduk.getNama();
	}

	public void setNama(String nama) {
		penduduk.setNama(nama);
	}

	@Transient
	public Kelamin getKelamin() {
		return penduduk.getKelamin();
	}

	public void setKelamin(Kelamin kelamin) {
		penduduk.setKelamin(kelamin);
	}

	@Transient
	public Date getTanggalLahir() {
		return penduduk.getTanggalLahir();
	}

	public void setTanggalLahir(Date tanggalLahir) {
		penduduk.setTanggalLahir(tanggalLahir);
	}

	@Transient
	public String getDarah() {
		return penduduk.getDarah();
	}

	public void setDarah(String darah) {
		penduduk.setDarah(darah);
	}

	@Transient
	public String getAgama() {
		return penduduk.getAgama();
	}

	public void setAgama(String agama) {
		penduduk.setAgama(agama);
	}

	@Transient
	public String getTelepon() {
		return penduduk.getTelepon();
	}

	public void setTelepon(String telepon) {
		penduduk.setTelepon(telepon);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nip == null) ? 0 : nip.hashCode());
		result = prime * result
				+ ((penduduk == null) ? 0 : penduduk.hashCode());
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
		Pegawai other = (Pegawai) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nip == null) {
			if (other.nip != null)
				return false;
		} else if (!nip.equals(other.nip))
			return false;
		if (penduduk == null) {
			if (other.penduduk != null)
				return false;
		} else if (!penduduk.equals(other.penduduk))
			return false;
		return true;
	}
}
