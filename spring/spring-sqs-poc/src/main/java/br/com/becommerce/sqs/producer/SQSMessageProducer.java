package br.com.becommerce.sqs.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import br.com.becommerce.sqs.dto.MessageDTO;

@Service
public class SQSMessageProducer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value(value = "${application.sqs.service.message.queue}")
	private String messageQueue;
	
    protected JmsTemplate defaultJmsTemplate;

    @Autowired
	public SQSMessageProducer(JmsTemplate defaultJmsTemplate) {
		super();
		this.defaultJmsTemplate = defaultJmsTemplate;
	}
    
    public void sendMessage(final String message) {
    	logger.info("Sending message: {}", message);
    	this.defaultJmsTemplate.convertAndSend(this.messageQueue, message);
    }
    
    public void sendMessage(final MessageDTO message) {
    	logger.info("Sending message: {}", message);
    	this.defaultJmsTemplate.convertAndSend(this.messageQueue, message);
    }
	
}
