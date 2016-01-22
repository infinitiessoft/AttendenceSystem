package service;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import service.impl.MailServiceImpl;

public class MailServiceImplTest extends ServiceTest {

	private MailServiceImpl mailService;
	private MailSender mailSender;

	@Before
	public void setUp() throws Exception {
		mailSender = context.mock(MailSender.class);
		mailService = new MailServiceImpl(mailSender);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendMail() {
		String to = "to";
		String subject = "subject";
		String body = "body";
		context.checking(new Expectations() {

			{
				exactly(1).of(mailSender).send(
						with(any(SimpleMailMessage.class)));
			}
		});
		mailService.sendMail(to, subject, body);
	}

}
