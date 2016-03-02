package com.dbsys.rs.serve.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dbsys.rs.serve.entity.Unit.TipeUnit;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Table(name = "pelayanan")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	name = "tipe",
	discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("PELAYANAN")
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "tipePelayanan"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Pelayanan.class, name = "PELAYANAN"),
	@JsonSubTypes.Type(value = PelayananTemporal.class, name = "TEMPORAL")
})
public class Pelayanan extends Tagihan {
	
	protected Tindakan tindakan;
	protected Pegawai pelaksana;
	
	// Untuk JSON bukan JPA
	private String tipePelayanan;

	public Pelayanan() {
		this("PELAYANAN");
	}
	
	public Pelayanan(String tipePelayanan) {
		super("PELAYANAN");
		this.tipePelayanan = tipePelayanan;
	}

	@Transient
	public String getTipePelayanan() {
		return tipePelayanan;
	}

	public void setTipePelayanan(String tipePelayanan) {
		this.tipePelayanan = tipePelayanan;
	}

	// @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}) // Testing
	@ManyToOne
	@JoinColumn(name = "tindakan")
	public Tindakan getTindakan() {
		return tindakan;
	}

	public void setTindakan(Tindakan tindakan) {
		this.tindakan = tindakan;
		this.tanggungan = tindakan;
	}

	// @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}) // Testing
	@ManyToOne
	@JoinColumn(name = "pelaksana")
	public Pegawai getPelaksana() {
		return pelaksana;
	}

	public void setPelaksana(Pegawai pelaksana) {
		this.pelaksana = pelaksana;
	}

	@Override
	@Transient
	public Long getTagihan() {
		/**
		 * Jika tindakan dilakukan di ICU, maka biaya tindakan dikali 2.
		 */
		if (TipeUnit.ICU.equals(unit.getTipe()))
			return (tindakan.getTarif() * 2) * jumlah + biayaTambahan;

		/**
		 * Jika tindakan dilakukan di UGD, maka biaya tindakan ditambah 25%.
		 */
		if (TipeUnit.UGD.equals(unit.getTipe())) {
			Long tambahanUgd = tindakan.getTarif() * 25 / 100;
			return (tindakan.getTarif() + tambahanUgd) * jumlah + biayaTambahan;
		}
		
		return tindakan.getTarif() * jumlah + biayaTambahan;
	}

	@Override
	@Transient
	public String getNama() {
		return tindakan.getNama();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((tindakan == null) ? 0 : tindakan.hashCode());
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
		Pelayanan other = (Pelayanan) obj;
		if (tindakan == null) {
			if (other.tindakan != null)
				return false;
		} else if (!tindakan.equals(other.tindakan))
			return false;
		return true;
	}
}
