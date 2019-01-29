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

public class AmazonSESEmailSender {
	
	private static Logger logger = LoggerFactory.getLogger(AmazonSESEmailSender.class);
	
	final String FROM = "marcelo.garcia@w3as.com.br";

	final String SUBJECT = "Email de verificação para completar o registro no sistema";

	final String HTMLBODY = "<h2> Bem vindo ao nosso sistema. <h2>"
			+ "<p> Para completar o processo de registro e ficar pronto para acessar o sistema, clique no link: </p>"
			+ "<p><a href='http://localhost:8010/?token=$tokenValue'> Validar token de acesso </a></p>" 
			+ "<br/><br/>"
			+ "<p>Nossos serviços irão te surpreender</p>";

	final String TEXTBODY = "Bem vindo ao nosso sistema. "
			+ "\n\nPara completar o processo de registro e ficar pronto para acessar o sistema, copie a url para o browser:"
			+ "\n\nhttp://localhost:8010/?token=$tokenValue" 
			+ "\n\nNossos serviços irão te surpreender";

	public void sendEmail(UserEntity entity) {
		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.US_EAST_1).build();
			
			String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", entity.getEmailVerificationToken());
			String textBodyWithToken = TEXTBODY.replace("$tokenValue", entity.getEmailVerificationToken());
			
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
			logger.info("Email sent to: " + entity.getEmail());
		} catch (Exception ex) {
			logger.error("The email was not sent.", ex);
		}
	}
}
