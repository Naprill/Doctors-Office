package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Represents results of patient's medical examination.
 * List of examinations is created by doctor
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MedicalExamination extends AbstractVersional {

	private String text;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User patient;
}
