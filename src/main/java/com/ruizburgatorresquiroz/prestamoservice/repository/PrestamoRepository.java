package com.ruizburgatorresquiroz.prestamoservice.repository;

import com.ruizburgatorresquiroz.prestamoservice.entity.Prestamo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

	List<Prestamo> findByUsuarioId(Long usuarioId);

	Optional<Prestamo> findByIdAndUsuarioId(Long id, Long usuarioId);

}
