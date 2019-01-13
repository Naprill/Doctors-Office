package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Represents result of patient's medical analysis.
 * Contains path to a file which can be a document like pdf,
 * or picture like jpg
 */
@Entity
@Getter
@Setter
@ToString(exclude = "patient")
@NoArgsConstructor
@AllArgsConstructor
public class Analysis extends AbstractVersional {

	private String pathToFile;

	private String fileName;

	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime date;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User patient;

	private String fileDownloadUrl;

}
