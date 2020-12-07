package com.lds.frmwk.graphql2.core;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table
@Entity
public class Coffee {
	
	@Id
	private String cid;
	private String name;
	public Coffee(String cid, String name) {
		this.cid = cid;
		this.name = name;
	}
}
