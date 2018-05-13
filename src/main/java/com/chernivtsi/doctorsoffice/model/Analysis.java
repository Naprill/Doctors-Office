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
 * Represents result of patient's medical analysis.
 * Contains path to a file which can be a document like pdf,
 * or picture like jpg
 */
@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Analysis extends AbstractVersional {

	private String pathToFile;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User patient;
}
