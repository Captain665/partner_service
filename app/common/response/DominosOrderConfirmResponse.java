package common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

//@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DominosOrderConfirmResponse {
	public String status;
	public List<Object> errors;
	public Object cartResponse;
	public Object irctcPayload;


	public DominosOrderConfirmResponse() {
	}

	public DominosOrderConfirmResponse(String status, List<Object> errors, Object cartResponse, Object irctcPayload) {
		this.status = status;
		this.errors = errors;
		this.cartResponse = cartResponse;
		this.irctcPayload = irctcPayload;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Object> getErrors() {
		return errors;
	}

	public void setErrors(List<Object> errors) {
		this.errors = errors;
	}

	public Object getCartResponse() {
		return cartResponse;
	}

	public void setCartResponse(Object cartResponse) {
		this.cartResponse = cartResponse;
	}

	public Object getIrctcPayload() {
		return irctcPayload;
	}

	public void setIrctcPayload(Object irctcPayload) {
		this.irctcPayload = irctcPayload;
	}
}
