package com.ruizburgatorresquiroz.prestamoservice.dto;

import com.ruizburgatorresquiroz.prestamoservice.entity.Prestamo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoResponse {

	private static final BigDecimal CIEN = new BigDecimal("100");
	private static final int ESCALA_MONEDA = 2;

	private Long id;
	private String cliente;
	private String dni;
	private BigDecimal monto;
	private BigDecimal interes;
	private LocalDate fechaPrestamo;
	private LocalDate fechaVencimiento;
	private Boolean pagado;
	private BigDecimal totalACobrar;

	public static PrestamoResponse fromEntity(Prestamo prestamo) {
		BigDecimal monto = prestamo.getMonto();
		BigDecimal interes = prestamo.getInteres();
		BigDecimal totalACobrar = monto.add(
				monto.multiply(interes).divide(CIEN, ESCALA_MONEDA, RoundingMode.HALF_UP));

		return PrestamoResponse.builder()
				.id(prestamo.getId())
				.cliente(prestamo.getCliente())
				.dni(prestamo.getDni())
				.monto(monto)
				.interes(interes)
				.fechaPrestamo(prestamo.getFechaPrestamo())
				.fechaVencimiento(prestamo.getFechaVencimiento())
				.pagado(prestamo.getPagado())
				.totalACobrar(totalACobrar)
				.build();
	}

}
