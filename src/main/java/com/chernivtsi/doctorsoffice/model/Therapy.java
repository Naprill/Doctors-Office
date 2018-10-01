package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents treatment of patient.
 * Each user can have a list of treatment notes
 * that is created by doctor
 */
@Entity
@Table(name = "therapy")
@Getter
@Setter
@ToString(exclude = "patient")
@NoArgsConstructor
public class Therapy extends AbstractVersional {

	private String text;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User patient;
}
