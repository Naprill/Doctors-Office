package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.token.AccountToken;
import com.chernivtsi.doctorsoffice.repository.AccountTokenRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer for {@link AccountToken} token
 */
@Service
public class AccountTokenService {

	private AccountTokenRepository accountTokenRepository;

	AccountTokenService(AccountTokenRepository accountTokenRepository) {
		this.accountTokenRepository = accountTokenRepository;
	}

	public AccountToken findByTokenUrl(String url) {
		return accountTokenRepository.findByToken(url);
	}

	public void deleteByToken(AccountToken token) {
		accountTokenRepository.delete(token);
	}

}