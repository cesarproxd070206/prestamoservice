package com.ruizburgatorresquiroz.prestamoservice.service;

public class PrestamoNotFoundException extends RuntimeException {

	public PrestamoNotFoundException() {
		super("Préstamo no encontrado");
	}

}
