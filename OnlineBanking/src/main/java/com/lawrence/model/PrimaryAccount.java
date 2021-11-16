package com.lawrence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class PrimaryAccount  implements Serializable{
   

    private static final long serialVersionUID = 5630149823348186262L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer accountNumber;
	private BigDecimal accountBalance;

	@OneToMany(mappedBy = "primaryAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	@Transient
	private List<PrimaryTransaction> primaryTransactionList;
}
