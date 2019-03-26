package com.chernivtsi.doctorsoffice.model;

import com.chernivtsi.doctorsoffice.model.base.AbstractVersional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a range of time during which a doctor ({@link Admin})
 * examines and talks to patient ({@link User})
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reception extends AbstractVersional {

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	private User user;

	@Column(nullable = false)
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate date;

	@Column(nullable = false)
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime intervalStart;

	@Column(nullable = false)
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime intervalEnd;

	@Enumerated(EnumType.STRING)
	private Interval interval;

	public Reception(LocalDate date, LocalDateTime intervalStart, LocalDateTime intervalEnd, Interval interval) {
		super();
		this.user = null;
		this.date = date;
		this.intervalStart = intervalStart;
		this.intervalEnd = intervalEnd;
		this.interval = interval;
	}
}
