package com.ruizburgatorresquiroz.prestamoservice.service;

import com.ruizburgatorresquiroz.prestamoservice.dto.PrestamoRequest;
import com.ruizburgatorresquiroz.prestamoservice.dto.PrestamoResponse;
import com.ruizburgatorresquiroz.prestamoservice.entity.Prestamo;
import com.ruizburgatorresquiroz.prestamoservice.entity.Usuario;
import com.ruizburgatorresquiroz.prestamoservice.repository.PrestamoRepository;
import com.ruizburgatorresquiroz.prestamoservice.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrestamoService {

	private final PrestamoRepository prestamoRepository;
	private final UsuarioRepository usuarioRepository;

	@Transactional(readOnly = true)
	public List<PrestamoResponse> listarPorUsuario(Long usuarioId) {
		return prestamoRepository.findByUsuarioId(usuarioId).stream()
				.map(PrestamoResponse::fromEntity)
				.toList();
	}

	@Transactional(readOnly = true)
	public PrestamoResponse obtenerPorId(Long id, Long usuarioId) {
		return PrestamoResponse.fromEntity(buscarDelUsuario(id, usuarioId));
	}

	@Transactional
	public PrestamoResponse crear(PrestamoRequest request, Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(UsuarioAutenticadoNoEncontradoException::new);

		Prestamo prestamo = Prestamo.builder()
				.cliente(request.getCliente())
				.dni(request.getDni())
				.monto(request.getMonto())
				.interes(request.getInteres())
				.fechaPrestamo(LocalDate.now())
				.fechaVencimiento(request.getFechaVencimiento())
				.pagado(request.getPagado() == null ? Boolean.FALSE : request.getPagado())
				.usuario(usuario)
				.build();

		return PrestamoResponse.fromEntity(prestamoRepository.save(prestamo));
	}

	@Transactional
	public PrestamoResponse actualizar(Long id, PrestamoRequest request, Long usuarioId) {
		Prestamo prestamo = buscarDelUsuario(id, usuarioId);
		prestamo.setCliente(request.getCliente());
		prestamo.setDni(request.getDni());
		prestamo.setMonto(request.getMonto());
		prestamo.setInteres(request.getInteres());
		prestamo.setFechaVencimiento(request.getFechaVencimiento());
		if (request.getPagado() != null) {
			prestamo.setPagado(request.getPagado());
		}
		return PrestamoResponse.fromEntity(prestamoRepository.save(prestamo));
	}

	@Transactional
	public void eliminar(Long id, Long usuarioId) {
		Prestamo prestamo = buscarDelUsuario(id, usuarioId);
		prestamoRepository.delete(prestamo);
	}

	private Prestamo buscarDelUsuario(Long id, Long usuarioId) {
		return prestamoRepository.findByIdAndUsuarioId(id, usuarioId)
				.orElseThrow(PrestamoNotFoundException::new);
	}

}
