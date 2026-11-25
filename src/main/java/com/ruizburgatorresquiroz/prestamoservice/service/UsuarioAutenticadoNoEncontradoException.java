package com.ruizburgatorresquiroz.prestamoservice.service;

public class UsuarioAutenticadoNoEncontradoException extends RuntimeException {

	public UsuarioAutenticadoNoEncontradoException() {
		super("Usuario autenticado no encontrado");
	}

}
