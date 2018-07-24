package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.token.ConfirmAccountToken;
import com.chernivtsi.doctorsoffice.repository.AccountTokenRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer for {@link ConfirmAccountToken} token
 */
@Service
public class AccountTokenService {

	private AccountTokenRepository accountTokenRepository;

	AccountTokenService(AccountTokenRepository accountTokenRepository) {
		this.accountTokenRepository = accountTokenRepository;
	}

	public ConfirmAccountToken findByTokenUrl(String url) {
		return accountTokenRepository.findByToken(url);
	}

	public void deleteByToken(ConfirmAccountToken token) {
		accountTokenRepository.delete(token);
	}

}