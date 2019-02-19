package teste;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceRequest implements Serializable, NotificationType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3528498057999972022L;

	private long id;

	private BigDecimal price;

	@JsonProperty(value = "price_list_name")
	private String priceListName;

	@JsonProperty(value = "price_list_id")
	private String priceListId;

	@JsonProperty(value = "meli_id")
	private String meliId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPriceListName() {
		return priceListName;
	}

	public void setPriceListName(String priceListName) {
		this.priceListName = priceListName;
	}

	public String getPriceListId() {
		return priceListId;
	}

	public void setPriceListId(String priceListId) {
		this.priceListId = priceListId;
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
		builder.append("PriceRequest [id=").append(id).append(", price=").append(price).append(", priceListName=")
				.append(priceListName).append(", priceListId=").append(priceListId).append(", meliId=").append(meliId)
				.append("]");
		return builder.toString();
	}

}
