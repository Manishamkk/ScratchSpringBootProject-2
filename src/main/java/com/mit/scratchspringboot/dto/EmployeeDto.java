package com.mit.scratchspringboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

	@NotNull(message = "name should  be required")
	private String name;

	@Email(message = " Enter the valid email")
	private String email;

	//@JsonIgnore
	//@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Enter the valid password")
	//@NotNull(message = "password should  be required")
	private String password;

	@NotNull(message = "mobile no  should  be required")
	private String mobileNo;

}
