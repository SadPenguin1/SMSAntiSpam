package com.isttmicroservice.smsantispam.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.isttmicroservice.smsantispam.dto.EmailDTO;
import com.isttmicroservice.smsantispam.dto.UserDTO;
import com.isttmicroservice.smsantispam.entity.User;
import com.isttmicroservice.smsantispam.repository.UserRepo;

public interface EmailService {
	void sendEmail(UserDTO userDTO,EmailDTO emailDTO );
}

@Service
class EmailServiceImpl implements EmailService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
	
    @Autowired
    UserRepo userRepo;

	@Override
	public void sendEmail(UserDTO userDTO,EmailDTO emailDTO ) {
		User user = userRepo.findByUsernameForgotPassword(userDTO.getUsername()).orElseThrow(NoResultException::new);
      try {
			logger.info("START... Sending email");

			MimeMessage email = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(email, StandardCharsets.UTF_8.name());
			emailDTO.setContent("Mật khẩu mới của bạn là : " + userDTO.getPassword());
			// load template email with content
			Context context = new Context();
			context.setVariable("username", userDTO.getUsername());
			context.setVariable("content", emailDTO.getContent());
			String html = templateEngine.process("email-forgetPassword", context);

			/// send email
			System.out.println(user.getEmail()+"  "+emailDTO.getFrom());
			helper.setTo(user.getEmail());
			helper.setText(html, true);
			helper.setSubject(emailDTO.getSubject());
			helper.setFrom(emailDTO.getFrom());
			javaMailSender.send(email);

			logger.info("END... Email sent success");
		} catch (MessagingException e) {
			logger.error("Email sent with error: " + e.getMessage());
		}
	}
}