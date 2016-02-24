package com.dbsys.rs.serve;

import com.dbsys.rs.ApplicationException;

/**
 * Exception yang akan dibuat ketika melakukan proses yang berhubungan dengan pasien yang sudah keluar.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class PasienOutException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasienOutException() {
		super();
	}

	public PasienOutException(String message) {
		super(message);
	}
}
