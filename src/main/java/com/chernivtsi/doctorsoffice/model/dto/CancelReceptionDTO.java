package com.chernivtsi.doctorsoffice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CancelReceptionDTO {

	private Long id;

	private Long userId;

	private String message;
}
