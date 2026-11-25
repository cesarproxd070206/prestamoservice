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
public class LoginRequest {

	@NotBlank
	@Email
	@Size(max = 120)
	private String email;

	@NotBlank
	@Size(max = 100)
	private String password;

}
