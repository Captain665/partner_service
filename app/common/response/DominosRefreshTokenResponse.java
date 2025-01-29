package common.response;

public class DominosRefreshTokenResponse {
	public String status;
	public String refresh_token;
	public String client_token;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getClient_token() {
		return client_token;
	}

	public void setClient_token(String client_token) {
		this.client_token = client_token;
	}

	public DominosRefreshTokenResponse() {
	}

	public DominosRefreshTokenResponse(String status, String refresh_token, String client_token) {
		this.status = status;
		this.refresh_token = refresh_token;
		this.client_token = client_token;
	}

	@Override
	public String toString() {
		return "DominosRefreshTokenResponse{" +
				"status='" + status + '\'' +
				", refresh_token='" + refresh_token + '\'' +
				", client_token='" + client_token + '\'' +
				'}';
	}
}
