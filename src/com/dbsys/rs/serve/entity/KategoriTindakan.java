package com.dbsys.rs.serve.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "kategori_tindakan")
public class KategoriTindakan {

	private Long id;
	private String nama;
	private KategoriTindakan parent;
	
	private List<Tindakan> daftarTindakan;
	private List<KategoriTindakan> daftarSubKategori;
	
	public KategoriTindakan() {
		super();
	}
	
	public KategoriTindakan(KategoriTindakan parent) {
		super();
		setParent(parent);
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nama")
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	@ManyToOne(targetEntity = KategoriTindakan.class)
	@JoinColumn(name = "parent")
	public KategoriTindakan getParent() {
		return parent;
	}

	public void setParent(KategoriTindakan parent) {
		this.parent = parent;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "kategori", cascade = CascadeType.PERSIST, targetEntity = Tindakan.class)
	public List<Tindakan> getDaftarTindakan() {
		return daftarTindakan;
	}

	public void setDaftarTindakan(List<Tindakan> daftarTindakan) {
		this.daftarTindakan = daftarTindakan;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, targetEntity = KategoriTindakan.class)
	public List<KategoriTindakan> getDaftarSubKategori() {
		return daftarSubKategori;
	}

	public void setDaftarSubKategori(List<KategoriTindakan> daftarSubKategori) {
		this.daftarSubKategori = daftarSubKategori;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((daftarSubKategori == null) ? 0 : daftarSubKategori
						.hashCode());
		result = prime * result
				+ ((daftarTindakan == null) ? 0 : daftarTindakan.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nama == null) ? 0 : nama.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		KategoriTindakan other = (KategoriTindakan) obj;
		if (daftarSubKategori == null) {
			if (other.daftarSubKategori != null)
				return false;
		} else if (!daftarSubKategori.equals(other.daftarSubKategori))
			return false;
		if (daftarTindakan == null) {
			if (other.daftarTindakan != null)
				return false;
		} else if (!daftarTindakan.equals(other.daftarTindakan))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nama == null) {
			if (other.nama != null)
				return false;
		} else if (!nama.equals(other.nama))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
}
