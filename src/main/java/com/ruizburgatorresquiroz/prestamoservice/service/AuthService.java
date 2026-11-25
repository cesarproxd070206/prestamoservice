package com.ruizburgatorresquiroz.prestamoservice.service;

import com.ruizburgatorresquiroz.prestamoservice.dto.AuthResponse;
import com.ruizburgatorresquiroz.prestamoservice.dto.LoginRequest;
import com.ruizburgatorresquiroz.prestamoservice.dto.PerfilResponse;
import com.ruizburgatorresquiroz.prestamoservice.dto.RegisterRequest;
import com.ruizburgatorresquiroz.prestamoservice.entity.Usuario;
import com.ruizburgatorresquiroz.prestamoservice.repository.UsuarioRepository;
import com.ruizburgatorresquiroz.prestamoservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private static final String ROL_USER = "USER";

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Transactional
	public AuthResponse register(RegisterRequest request) {
		if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException();
		}

		Usuario usuario = Usuario.builder()
				.nombre(request.getNombre())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.rol(ROL_USER)
				.build();
		usuarioRepository.save(usuario);

		return authResponse(usuario);
	}

	@Transactional(readOnly = true)
	public AuthResponse login(LoginRequest request) {
		Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BadCredentialsException("Credenciales incorrectas"));

		if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
			throw new BadCredentialsException("Credenciales incorrectas");
		}

		return authResponse(usuario);
	}

	@Transactional(readOnly = true)
	public PerfilResponse perfil(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(UsuarioAutenticadoNoEncontradoException::new);
		return PerfilResponse.builder()
				.nombre(usuario.getNombre())
				.email(usuario.getEmail())
				.build();
	}

	private AuthResponse authResponse(Usuario usuario) {
		return AuthResponse.builder()
				.token(jwtService.generateToken(usuario.getEmail()))
				.email(usuario.getEmail())
				.nombre(usuario.getNombre())
				.build();
	}

}
