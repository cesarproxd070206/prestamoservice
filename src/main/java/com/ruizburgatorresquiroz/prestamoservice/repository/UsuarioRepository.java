package com.ruizburgatorresquiroz.prestamoservice.repository;

import com.ruizburgatorresquiroz.prestamoservice.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

}
