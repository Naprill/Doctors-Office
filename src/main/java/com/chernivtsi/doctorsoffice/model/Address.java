package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Address extends AbstractVersional {

	private String region;

	private String district;

	private String cityOrVillage;

	private String street;

	private String houseNumber;

}
