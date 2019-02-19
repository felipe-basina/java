package teste;

public class Main {

	public static void main(String[] args) {
		runPrice();
		runStock();
	}
	
	private static void runPrice() {
		final String priceJson = "{\"payload\":\"{\\\"id\\\":1,\\\"price\\\":10.59,\\\"price_list_name\\\":\\\"PRICE_LIST 1\\\",\\\"price_list_id\\\":\\\"100001\\\",\\\"meli_id\\\":\\\"123456789\\\"}\",\"type\":\"PRICE\"}";
		NotificationRequest notificationRequest = new NotificationRequest(priceJson);
		PriceRequest priceRequest = notificationRequest.getNotificationType();
		print(priceRequest);
		print(notificationRequest.getRequestDTO());
	}
	
	private static void runStock() {
		final String stockJson = "{\"payload\":\"{\\\"id\\\":1,\\\"amount\\\":27,\\\"product\\\":\\\"100001\\\",\\\"meli_id\\\":\\\"123456789\\\"}\",\"type\":\"STOCK\"}";
		NotificationRequest notificationRequest = new NotificationRequest(stockJson);
		StockRequest stockRequest = notificationRequest.getNotificationType();
		print(stockRequest);
		print(notificationRequest.getRequestDTO());
	}
	
	private static void print(NotificationType notificationType) {
		System.out.println(notificationType);
	}
	
	private static void print(RequestDTO requestDTO) {
		System.out.println(requestDTO);
	}

}
