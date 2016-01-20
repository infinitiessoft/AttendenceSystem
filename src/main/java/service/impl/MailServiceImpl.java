package service.impl;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import service.MailService;

public class MailServiceImpl implements MailService {

	private MailSender mailSender;

	public MailServiceImpl(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * This method will send compose and send the message
	 * */
	@Async
	@Override
	public void sendMail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}

}
