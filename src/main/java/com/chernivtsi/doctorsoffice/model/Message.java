package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "messages")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message extends AbstractVersional {

	private Long fromWhom;

	private Long toWhom;

	private String text;

	private Role fromRole;

	private Role toRole;

}
