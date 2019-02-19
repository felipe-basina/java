package teste;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NotificationRequest {

	private RequestDTO requestDTO;
	
	private NotificationType notificationType;
	
	private Class<? extends NotificationType> type;
	
	private Gson gson = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.create();

	public NotificationRequest(final String request) {
		this.requestDTO = this.gson.fromJson(request, RequestDTO.class);
		this.type = NotificationTypeFactory.geNotificationTypeFrom(this.requestDTO.getType());
		this.notificationType = this.gson.fromJson(this.requestDTO.getPayload(), this.type);
	}
	
	@SuppressWarnings("unused")
	private NotificationRequest() {}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getNotificationType() {
		return (T) this.type.cast(this.notificationType);
	}

}
