package br.com.curso.spring.shared;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import br.com.curso.spring.model.UserEntity;

public class AmazonSESResetPasswordSender {
	
	private static Logger logger = LoggerFactory.getLogger(AmazonSESResetPasswordSender.class);
	
	final String FROM = "marcelo.garcia@w3as.com.br";

	final String SUBJECT = "Email para confirmação de reset de senha";

	final String HTMLBODY = "<h2> Confirmação de reset de senha. <h2>"
			+ "<p> Olá $userName </p>"
			+ "<p> Para completar o processo de reset de senha, clique no link:  </p>"
			+ "<p><a href='http://localhost:8010/reset-password?token=$tokenValue'> Reset de senha </a></p>" 
			+ "<br/><br/>";

	final String TEXTBODY = "Confirmação de reset de senha. "
			+ "\n\nOlá $userName"
			+ "\n\nPara completar o processo de reset de senha, copie o link e cole no navegador:"
			+ "\n\nhttp://localhost:8010/reset-password?token=$tokenValue";

	public void sendEmail(UserEntity entity, String token) {
		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_EAST_1).build();
			
			String htmlBodyWithToken = HTMLBODY
										.replace("$userName", entity.getFirstName())
										.replace("$tokenValue", token);
			String textBodyWithToken = TEXTBODY
										.replace("$userName", entity.getFirstName())
										.replace("$tokenValue", token);
			
			SendEmailRequest request = new SendEmailRequest()
					.withDestination(new Destination().withToAddresses(entity.getEmail()))
					.withMessage(new Message()
							.withBody(new Body()	
									.withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
									.withText(new Content().withCharset("UTF-8").withData(textBodyWithToken))
							)
							.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT))
					)
					.withSource(FROM);
			
			client.sendEmail(request);
			logger.info("Reset password sent to: " + entity.getEmail());
		} catch (Exception ex) {
			logger.error("The reset password email was not sent.", ex);
		}
	}
}
