package com.dbsys.rs.serve.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DOKTER")
public class Dokter extends Pegawai {

	public enum Spesialisasi {
		UMUM, MATA, BEDAH, KANDUNGAN
	}

	private Spesialisasi spesialisasi;

	public Dokter() {
		super("DOKTER");
	}

	public Dokter(Spesialisasi spesialisasi) {
		this();
		setSpesialisasi(spesialisasi);
	}

	@Column(name = "spesialisasi")
	public Spesialisasi getSpesialisasi() {
		return spesialisasi;
	}

	public void setSpesialisasi(Spesialisasi spesialisasi) {
		this.spesialisasi = spesialisasi;
	}
}
