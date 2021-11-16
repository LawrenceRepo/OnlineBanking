package com.lawrence.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipientDto {

	private String name;
	private String email;
	private String phone;
	private String accountNumber;
	private String description;


	private UserDto userDto;
}
