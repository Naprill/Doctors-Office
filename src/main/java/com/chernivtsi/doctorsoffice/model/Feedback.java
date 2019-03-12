package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@ToString
public class Feedback extends AbstractIdentifiable {

	private String author;

	@Column(columnDefinition = "text", length = 10485760)
	private String text;
}