package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.token.ConfirmAccountToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTokenRepository extends JpaRepository<ConfirmAccountToken, Long> {
	ConfirmAccountToken findByToken(String token);
}
