package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

	@Column(columnDefinition="text", length=10485760)
	private String text;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User patient;

	public Therapy(Long id, String text, LocalDateTime createdAt, User patient) {
		this.id = id;
		this.text = text;
		this.createdAt = createdAt;
		this.patient = patient;
	}
}
