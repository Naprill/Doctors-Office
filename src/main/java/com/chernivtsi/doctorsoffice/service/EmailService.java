package com.chernivtsi.doctorsoffice.service;

import com.chernivtsi.doctorsoffice.model.Mail;
import com.chernivtsi.doctorsoffice.model.User;
import com.chernivtsi.doctorsoffice.model.token.AccountToken;
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

@Service
public class EmailService {

	private JavaMailSender emailSender;
	private SpringTemplateEngine templateEngine;


	public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
		this.emailSender = emailSender;
		this.templateEngine = templateEngine;
	}

	@Async
	public void sendRegistrationEmail(AccountToken token, User user, String url) {

		Mail mail = new Mail();
		mail.setFrom("no-reply@doctor-patratiy-office.com");
		mail.setTo(user.getEmail());
		mail.setSubject("Реєстрація персонального кабінету в медкабінеті лікаря Патратій Марини Володимирівни");

		Map<String, Object> model = new HashMap<>();
		model.put("token", token);
		model.put("userFName", user.getFirstName());
		model.put("userLName", user.getLastName());
		model.put("signature", "https://doctors-office.com/");
		model.put("confirmUrl", url + "/confirm-account?token=" + token.getToken());
		mail.setModel(model);
		sendEmail(mail, "/layouts/registrationEmailTemplate");
	}

	private void sendEmail(Mail mail, String template) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Context context = new Context();
			context.setVariables(mail.getModel());
			String html = templateEngine.process(template, context);

			helper.setTo(mail.getTo());
			helper.setText(html, true);
			helper.setSubject(mail.getSubject());
			helper.setFrom(mail.getFrom());

			emailSender.send(message);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}