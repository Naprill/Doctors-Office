package com.chernivtsi.doctorsoffice.model.token;

import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class ConfirmAccountToken extends AbstractIdentifiable {

	@Column(nullable = false, unique = true)
	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

}
