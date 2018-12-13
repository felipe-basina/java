package br.com.becommerce.sqs.client;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import br.com.becommerce.sqs.dto.MessageDTO;

@Service
public class SQSMessageClient {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @JmsListener(destination = "${application.sqs.service.message.queue}")
    public void createThumbnail(String message) throws JMSException {
        try {
            logger.info("Message received: {}", message);
        } catch (Exception ex) {
        	logger.error("Encountered error while parsing message.", ex);
            throw new JMSException("Encountered error while parsing message.");
        }
    }
	
    @JmsListener(destination = "${application.sqs.service.message.queue}")
    public void createThumbnail(MessageDTO message) throws JMSException {
        try {
            logger.info("Message received: {}", message);
        } catch (Exception ex) {
        	logger.error("Encountered error while parsing message.", ex);
            throw new JMSException("Encountered error while parsing message.");
        }
    }
	
}
