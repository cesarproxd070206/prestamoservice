package com.ruizburgatorresquiroz.prestamoservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

	@NotBlank
	@Size(max = 100)
	private String nombre;

	@NotBlank
	@Email
	@Size(max = 120)
	private String email;

	@NotBlank
	@Size(min = 8, max = 100)
	private String password;

}
