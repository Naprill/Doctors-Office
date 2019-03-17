package com.chernivtsi.doctorsoffice.model.token;

import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.base.AbstractIdentifiable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AccountToken extends AbstractIdentifiable {

	@Column(nullable = false, unique = true)
	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	@Column(nullable = false)
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime expiryDate;

	public void setExpiryDate(int minutes){
		this.expiryDate = LocalDateTime.now().plusMinutes(minutes);
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expiryDate);
	}

}
