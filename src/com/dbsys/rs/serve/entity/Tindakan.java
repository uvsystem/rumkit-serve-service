package com.dbsys.rs.serve.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dbsys.rs.CodedEntity;
import com.dbsys.rs.DateUtil;
import com.dbsys.rs.Kelas;
import com.dbsys.rs.Tanggungan;
import com.dbsys.rs.Penanggung;

@Entity
@Table(name = "tindakan")
public class Tindakan implements Tanggungan, CodedEntity {
	
	public enum SatuanTindakan {
		TINDAKAN, HARI, JAM
	}

	protected Long id;
	protected String kode;
	protected String nama;
	protected Long tarif;
	protected KategoriTindakan kategori;
	protected Kelas kelas;
	protected Penanggung penanggung;
	protected SatuanTindakan satuan;
	protected String keterangan;
	
	public Tindakan() {
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

	@Override
	public String generateKode() {
		return createKode();
	}
	
	public static String createKode() {
		Integer d = Math.abs(DateUtil.getDate().hashCode());
		Integer t = Math.abs(DateUtil.getTime().hashCode());
		
		return String.format("70%s00%s", d, t);
	}
	
	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@Column(name = "tarif")
	public Long getTarif() {
		return tarif;
	}

	public void setTarif(Long tarif) {
		this.tarif = tarif;
	}

	@ManyToOne
	@JoinColumn(name = "kategori")
	public KategoriTindakan getKategori() {
		return kategori;
	}

	public void setKategori(KategoriTindakan kategori) {
		this.kategori = kategori;
	}

	@Column(name = "kelas")
	public Kelas getKelas() {
		return kelas;
	}

	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}

	@Override
	@Column(name = "penanggung")
	public Penanggung getPenanggung() {
		return penanggung;
	}

	public void setPenanggung(Penanggung penanggung) {
		this.penanggung = penanggung;
	}

	@Column(name = "satuan")
	public SatuanTindakan getSatuan() {
		return satuan;
	}

	public void setSatuan(SatuanTindakan satuan) {
		this.satuan = satuan;
	}

	@Column(name = "keterangan")
	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((kategori == null) ? 0 : kategori.hashCode());
		result = prime * result + ((kelas == null) ? 0 : kelas.hashCode());
		result = prime * result
				+ ((keterangan == null) ? 0 : keterangan.hashCode());
		result = prime * result + ((kode == null) ? 0 : kode.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result + ((satuan == null) ? 0 : satuan.hashCode());
		result = prime * result
				+ ((penanggung == null) ? 0 : penanggung.hashCode());
		result = prime * result + ((tarif == null) ? 0 : tarif.hashCode());
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
		Tindakan other = (Tindakan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (kategori == null) {
			if (other.kategori != null)
				return false;
		} else if (!kategori.equals(other.kategori))
			return false;
		if (kelas != other.kelas)
			return false;
		if (keterangan == null) {
			if (other.keterangan != null)
				return false;
		} else if (!keterangan.equals(other.keterangan))
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
		if (satuan != other.satuan)
			return false;
		if (penanggung != other.penanggung)
			return false;
		if (tarif == null) {
			if (other.tarif != null)
				return false;
		} else if (!tarif.equals(other.tarif))
			return false;
		return true;
	}
}
