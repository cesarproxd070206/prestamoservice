package com.ruizburgatorresquiroz.prestamoservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prestamo")
public class Prestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String cliente;

	@NotBlank
	@Size(max = 8)
	@Column(nullable = false, length = 8)
	private String dni;

	@NotNull
	@Digits(integer = 8, fraction = 2)
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal monto;

	@NotNull
	@Digits(integer = 3, fraction = 2)
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal interes;

	@NotNull
	@Column(nullable = false)
	private LocalDate fechaPrestamo;

	@NotNull
	@Column(nullable = false)
	private LocalDate fechaVencimiento;

	@NotNull
	@Builder.Default
	@Column(nullable = false)
	private Boolean pagado = false;

	@NotNull
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

}
