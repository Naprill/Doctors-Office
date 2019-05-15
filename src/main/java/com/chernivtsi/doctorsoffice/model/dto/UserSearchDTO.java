package com.chernivtsi.doctorsoffice.model.dto;

import com.chernivtsi.doctorsoffice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSearchDTO {

	private Long id;

	private String name;

	public static UserSearchDTO toSearchDTO(User user) {
		return new UserSearchDTO(user.getId(), user.getFirstName() + " " + user.getLastName());
	}
}
