package com.ruizburgatorresquiroz.prestamoservice.service;

public class EmailAlreadyExistsException extends RuntimeException {

	public EmailAlreadyExistsException() {
		super("El email ya está registrado");
	}

}
