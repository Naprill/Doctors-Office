package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.token.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<PasswordResetToken, Long>{
    PasswordResetToken findByToken(String token);
}
