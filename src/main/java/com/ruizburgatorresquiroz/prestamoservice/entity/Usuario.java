package com.ruizburgatorresquiroz.prestamoservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String nombre;

	@NotBlank
	@Email
	@Size(max = 120)
	@Column(nullable = false, unique = true, length = 120)
	private String email;

	@NotBlank
	@Size(max = 100)
	@Column(nullable = false, length = 100)
	private String password;

	@NotBlank
	@Size(max = 20)
	@Builder.Default
	@Column(nullable = false, length = 20)
	private String rol = "USER";

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "usuario")
	private List<Prestamo> prestamos = new ArrayList<>();

}
