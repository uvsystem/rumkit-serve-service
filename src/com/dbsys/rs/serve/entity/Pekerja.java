package com.dbsys.rs.serve.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PEKERJA")
public class Pekerja extends Pegawai {

	public Pekerja() {
		super("PEKERJA");
	}
}
