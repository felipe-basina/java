package teste;

import java.io.Serializable;

public class RequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -554189425000406191L;

	private String payload;

	private String type;
	
	private RequestDTO() {}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestDTO [payload=").append(payload).append(", type=").append(type).append("]");
		return builder.toString();
	}

}
