package common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderConfirmResponse {
	public String orderId;
	public String aggregatorOrderId;
	public String status;

	public OrderConfirmResponse() {
	}

	public OrderConfirmResponse(String orderId, String aggregatorOrderId, String status) {
		this.orderId = orderId;
		this.aggregatorOrderId = aggregatorOrderId;
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAggregatorOrderId() {
		return aggregatorOrderId;
	}

	public void setAggregatorOrderId(String aggregatorOrderId) {
		this.aggregatorOrderId = aggregatorOrderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
