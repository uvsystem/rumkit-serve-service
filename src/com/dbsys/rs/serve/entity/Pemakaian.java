package com.dbsys.rs.serve.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dbsys.rs.CodedEntity;
import com.dbsys.rs.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pemakaian")
public class Pemakaian extends Tagihan implements CodedEntity {

	private Barang barang;
	private String nomorResep;

	public Pemakaian() {
		super("PEMAKAIAN");
	}
	
	@ManyToOne
	@JoinColumn(name = "barang")
	public Barang getBarang() {
		return barang;
	}

	public void setBarang(Barang barang) {
		this.barang = barang;
		this.tanggungan = barang;
	}

	@Column(name = "nomor_resep")
	public String getNomorResep() {
		return nomorResep;
	}

	public void setNomorResep(String nomorResep) {
		this.nomorResep = nomorResep;
	}

	@Override
	@Transient
	public Long getTagihan() {
		return barang.getHarga() * jumlah + biayaTambahan;
	}
	
	@Override
	@Transient
	public String getNama() {
		return barang.getNama();
	}

	@Override
	public String generateKode() {
		return createKode();
	}
	
	public static String createKode() {
		Integer d = Math.abs(DateUtil.getDate().hashCode());
		Integer t = Math.abs(DateUtil.getTime().hashCode());
		
		return String.format("20%s00%s", d, t);
	}

	@Override
	@JsonIgnore
	@Transient
	public String getKode() {
		return getNomorResep();
	}

	@Override
	public void setKode(String kode) {
		setNomorResep(kode);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((barang == null) ? 0 : barang.hashCode());
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
		Pemakaian other = (Pemakaian) obj;
		if (barang == null) {
			if (other.barang != null)
				return false;
		} else if (!barang.equals(other.barang))
			return false;
		return true;
	}
}
