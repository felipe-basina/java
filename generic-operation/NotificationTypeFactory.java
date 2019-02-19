package teste;

public class NotificationTypeFactory {

	private NotificationTypeFactory() {}
	
	public static Class<? extends NotificationType> geNotificationTypeFrom(final String type) {
		switch (type) {
		case "PRICE":
			return PriceRequest.class;

		case "STOCK":
			return StockRequest.class;
			
		default:
			break;
		}
		throw new IllegalArgumentException("Invalid type: " + type);
	}
	
}
