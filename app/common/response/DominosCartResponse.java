package common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DominosCartResponse {
	public Boolean status;
	public String errorMsg;

	public DominosCartResponse() {
	}

	public DominosCartResponse(Boolean status, String errorMsg) {
		this.status = status;
		this.errorMsg = errorMsg;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
