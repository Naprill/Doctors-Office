package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.token.AccountToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTokenRepository extends JpaRepository<AccountToken, Long> {
	AccountToken findByToken(String token);
}
