package com.chernivtsi.doctorsoffice.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FeedbackDTO {

	private Boolean anonymous = false;

	@NotBlank
	private String text;
}
