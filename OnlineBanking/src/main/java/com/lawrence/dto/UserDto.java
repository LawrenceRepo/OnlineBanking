package com.lawrence.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.lawrence.security.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {


	private Long userId;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private boolean enabled = true;
	private String email;


	private PrimaryAccountDto primaryAccountDto;

	private SavingsAccountDto savingsAccountDto;


	private List<AppointmentDto> appointmentList;

	private List<RecipientDto> recipientList;

	
	private Set<UserRole> userRoles = new HashSet<>();



}
