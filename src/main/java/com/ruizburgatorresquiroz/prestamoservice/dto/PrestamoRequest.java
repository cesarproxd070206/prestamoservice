package com.ruizburgatorresquiroz.prestamoservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoRequest {

	@NotBlank
	@Size(max = 100)
	private String cliente;

	@NotBlank
	@Size(min = 8, max = 8)
	private String dni;

	@NotNull
	@Positive
	@Digits(integer = 8, fraction = 2)
	private BigDecimal monto;

	@NotNull
	@DecimalMin(value = "0.00")
	@Digits(integer = 3, fraction = 2)
	private BigDecimal interes;

	@NotNull
	private LocalDate fechaVencimiento;

	private Boolean pagado;

}
