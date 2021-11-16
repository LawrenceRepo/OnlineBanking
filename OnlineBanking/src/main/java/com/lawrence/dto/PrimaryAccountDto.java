package com.lawrence.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrimaryAccountDto {

	
	private Integer accountNumber;
	private BigDecimal accountBalance;

	private List<PrimaryTransactionDto> primaryTransactionList;
}
