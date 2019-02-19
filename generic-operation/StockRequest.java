package teste;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockRequest implements Serializable, NotificationType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1713552972575029147L;

	private long id;

	private int amount;

	private String product;

	@JsonProperty(value = "meli_id")
	private String meliId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getMeliId() {
		return meliId;
	}

	public void setMeliId(String meliId) {
		this.meliId = meliId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StockRequest [id=").append(id).append(", amount=").append(amount).append(", product=")
				.append(product).append(", meliId=").append(meliId).append("]");
		return builder.toString();
	}

}
