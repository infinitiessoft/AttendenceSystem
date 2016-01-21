package mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.MailService;

public class MockMailService implements MailService {

	private static final Logger logger = LoggerFactory
			.getLogger(MockMailService.class);

	@Override
	public void sendMail(String to, String subject, String body) {
		logger.debug("to:{}, subject:{}, body:{}", new Object[] { to, subject,
				body });
	}

}
