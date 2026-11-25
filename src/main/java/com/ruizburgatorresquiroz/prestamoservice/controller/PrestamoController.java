package com.ruizburgatorresquiroz.prestamoservice.controller;

import com.ruizburgatorresquiroz.prestamoservice.dto.PrestamoRequest;
import com.ruizburgatorresquiroz.prestamoservice.dto.PrestamoResponse;
import com.ruizburgatorresquiroz.prestamoservice.repository.UsuarioRepository;
import com.ruizburgatorresquiroz.prestamoservice.service.PrestamoNotFoundException;
import com.ruizburgatorresquiroz.prestamoservice.service.PrestamoService;
import com.ruizburgatorresquiroz.prestamoservice.service.UsuarioAutenticadoNoEncontradoException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

	private final PrestamoService prestamoService;
	private final UsuarioRepository usuarioRepository;

	@GetMapping
	public List<PrestamoResponse> listar() {
		return prestamoService.listarPorUsuario(usuarioAutenticadoId());
	}

	@GetMapping("/{id}")
	public PrestamoResponse obtener(@PathVariable Long id) {
		return prestamoService.obtenerPorId(id, usuarioAutenticadoId());
	}

	@PostMapping
	public ResponseEntity<PrestamoResponse> crear(@Valid @RequestBody PrestamoRequest request) {
		PrestamoResponse response = prestamoService.crear(request, usuarioAutenticadoId());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public PrestamoResponse actualizar(@PathVariable Long id, @Valid @RequestBody PrestamoRequest request) {
		return prestamoService.actualizar(id, request, usuarioAutenticadoId());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		prestamoService.eliminar(id, usuarioAutenticadoId());
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(PrestamoNotFoundException.class)
	public ResponseEntity<Map<String, String>> handlePrestamoNotFound(PrestamoNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
	}

	@ExceptionHandler(UsuarioAutenticadoNoEncontradoException.class)
	public ResponseEntity<Map<String, String>> handleUsuarioNoEncontrado(UsuarioAutenticadoNoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
	}

	private Long usuarioAutenticadoId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getName() == null) {
			throw new UsuarioAutenticadoNoEncontradoException();
		}
		return usuarioRepository.findByEmail(authentication.getName())
				.orElseThrow(UsuarioAutenticadoNoEncontradoException::new)
				.getId();
	}

}
