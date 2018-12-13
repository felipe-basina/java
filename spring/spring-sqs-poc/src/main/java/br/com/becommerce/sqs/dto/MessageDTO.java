package br.com.becommerce.sqs.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7583573590144256688L;

	private String message;

	private LocalDateTime localDateTime;

	public MessageDTO(String message) {
		super();
		this.message = message;
		this.localDateTime = LocalDateTime.now();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageDTO [message=").append(message).append(", localDateTime=").append(localDateTime)
				.append("]");
		return builder.toString();
	}

}
