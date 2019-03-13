package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Feedback extends AbstractIdentifiable {

	private String author;

	@Column(columnDefinition = "text", length = 10485760)
	private String text;

	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate date;
}