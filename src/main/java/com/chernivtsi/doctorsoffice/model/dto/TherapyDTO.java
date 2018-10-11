package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.Therapy;
import com.chernivtsi.doctorsoffice.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TherapyDTO {

	private Long id;
	
	private String text;
	
	private Long patient;

	private LocalDateTime createdAt;

	public Therapy toEntity(User user) {
		return new Therapy(
				this.id,
				this.text,
				this.createdAt,
				user);
	}

	public TherapyDTO(Therapy therapy) {
		this.id = therapy.getId();
		this.text = therapy.getText();
		this.patient = therapy.getPatient().getId();
		this.createdAt = therapy.getCreatedAt();
	}
}
