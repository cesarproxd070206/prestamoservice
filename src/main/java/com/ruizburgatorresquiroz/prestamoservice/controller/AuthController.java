package com.ruizburgatorresquiroz.prestamoservice.controller;

import com.ruizburgatorresquiroz.prestamoservice.dto.AuthResponse;
import com.ruizburgatorresquiroz.prestamoservice.dto.LoginRequest;
import com.ruizburgatorresquiroz.prestamoservice.dto.PerfilResponse;
import com.ruizburgatorresquiroz.prestamoservice.dto.RegisterRequest;
import com.ruizburgatorresquiroz.prestamoservice.service.AuthService;
import com.ruizburgatorresquiroz.prestamoservice.service.EmailAlreadyExistsException;
import com.ruizburgatorresquiroz.prestamoservice.service.UsuarioAutenticadoNoEncontradoException;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
		return authService.register(request);
	}

	@PostMapping("/login")
	public AuthResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@GetMapping("/me")
	public PerfilResponse me(Authentication authentication) {
		if (authentication == null || authentication.getName() == null || authentication.getName().isBlank()) {
			throw new UsuarioAutenticadoNoEncontradoException();
		}
		return authService.perfil(authentication.getName());
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
		return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDuplicateEmail() {
		return ResponseEntity.badRequest().body(Map.of("message", "El email ya está registrado"));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
	}

	@ExceptionHandler(UsuarioAutenticadoNoEncontradoException.class)
	public ResponseEntity<Map<String, String>> handleUsuarioNoEncontrado(UsuarioAutenticadoNoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
	}

}
