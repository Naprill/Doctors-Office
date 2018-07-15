package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Mail;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.repository.TokenRepository;
import com.chernivtsi.doctorsoffice.model.token.PasswordResetToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EmailService {

    private JavaMailSender emailSender;
    private SpringTemplateEngine templateEngine;
    private TokenRepository tokenRepository;


    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine, TokenRepository tokenRepository) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.tokenRepository = tokenRepository;
    }

    @Async
    public void createAndSendEmail(User user, Integer time, String url) {
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(time);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("no-reply@doctor-patratiy-office.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Реєстрація персонального кабінету в медкабінеті лікаря Патратій Марини Володимирівни");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("userFName", user.getFirstName());
        model.put("userLName", user.getLastName());
        model.put("signature", "https://doctors-office.com/");
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        sendEmail(mail);
    }

	private void sendEmail(Mail mail) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Context context = new Context();
			context.setVariables(mail.getModel());
			String html = templateEngine.process("/layouts/mail", context);

			helper.setTo(mail.getTo());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom());

			emailSender.send(message);

		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}
}