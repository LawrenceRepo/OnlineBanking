package com.lawrence.dto;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingsTransactionDto {


	private Long id;
	
	private Date date;
	private String description;
	private String type;
	private String status;
	private double amount;
	private BigDecimal availableBalance;

	
	private SavingsAccountDto savingsAccountDto;

}
