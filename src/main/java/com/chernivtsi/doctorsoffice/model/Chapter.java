package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString
public class Chapter extends AbstractIdentifiable {

	@NotBlank(message = "Назва обов'язкова")
	private String title;

	@NotBlank(message = "Текст обов'язковий")
	@Column(columnDefinition = "text", length = 10485760)
	private String text;
}
