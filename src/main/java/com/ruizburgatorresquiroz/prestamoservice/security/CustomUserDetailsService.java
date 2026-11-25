package com.ruizburgatorresquiroz.prestamoservice.security;

import com.ruizburgatorresquiroz.prestamoservice.entity.Usuario;
import com.ruizburgatorresquiroz.prestamoservice.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private static final String ROLE_PREFIX = "ROLE_";

	private final UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		String rol = usuario.getRol();
		String authority = rol.startsWith(ROLE_PREFIX) ? rol : ROLE_PREFIX + rol;

		return User.withUsername(usuario.getEmail())
				.password(usuario.getPassword())
				.authorities(authority)
				.build();
	}

}
