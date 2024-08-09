package com.designershop.mail;

import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;

	private final String FROM = "DesignerShop<designershop0715@gmail.com>";

	public void sendEmail(String[] receivers, String[] cc, String[] bcc, String subject, String content)
			throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(FROM);
		helper.setTo(receivers);
		helper.setCc(cc);
		helper.setBcc(bcc);
		helper.setSubject(subject);
		helper.setText(content);

		javaMailSender.send(message);
	}

	public void sendEmailWithTemplate(String[] receivers, String[] cc, String[] bcc, String subject, String template,
			Map<String, Object> variables) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(FROM);
		helper.setTo(receivers);
		helper.setCc(cc);
		helper.setBcc(bcc);
		helper.setSubject(subject);

		Context context = new Context();
		if (!variables.isEmpty()) {
			context.setVariables(variables);
		}
		String emailContent = templateEngine.process(template, context);
		helper.setText(emailContent, true);

		javaMailSender.send(message);
	}
}
