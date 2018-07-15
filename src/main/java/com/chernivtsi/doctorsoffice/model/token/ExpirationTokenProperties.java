package com.chernivtsi.doctorsoffice.model.token;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tokenExpiration")
@Getter
@Setter
public class ExpirationTokenProperties {

    private Integer resetPassword;

    private Integer verificationTime;
}
