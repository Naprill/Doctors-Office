package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Interval;
import com.chernivtsi.doctorsoffice.model.Reception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class ReceptionDTO {

	private Long id;

	private Long userId;

	private String userFirstName;

	private String userLastName;

	private LocalDate date;

	private LocalTime intervalStart;

	private LocalTime intervalEnd;

	private Long duration;

	private Interval interval;

	public static ReceptionDTO convertToDto(Reception reception) {
		if (reception.getUser() != null) {
			return new ReceptionDTO(
					reception.getId(),
					reception.getUser().getId(),
					reception.getUser().getFirstName(),
					reception.getUser().getLastName(),
					reception.getDate(),
					reception.getIntervalStart(),
					reception.getIntervalEnd(),
					Duration.between(reception.getIntervalStart(), reception.getIntervalEnd()).toMinutes(),
					Interval.BUSY);
		} else {
			return new ReceptionDTO(
					reception.getId(),
					null,
					null,
					null,
					reception.getDate(),
					reception.getIntervalStart(),
					reception.getIntervalEnd(),
					Duration.between(reception.getIntervalStart(), reception.getIntervalEnd()).toMinutes(),
					Interval.FREE);
		}
	}
}
