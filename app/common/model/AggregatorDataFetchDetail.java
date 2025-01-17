package common.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "aggregator_data_fetch_details")
public class AggregatorDataFetchDetail extends BaseModel {

	@Column(name = "vendor_id")
	private Long vendorId;
	@Column(name = "outlet_url")
	private String outletUrl;
	@Column(name = "menu_url")
	private String menuUrl;
	@Column(name = "order_url")
	private String orderUrl;
	@Column(name = "order_send_before_mins")
	private Integer orderSendBeforeMins;
	@Column(name = "auth_key")
	private String authKey;
	@Column(name = "auth_value")
	private String authValue;
	@Column(name = "parser_type")
	private String parserType;
	@Column(name = "reverse_order_url")
	private String reverseOrderUrl;
	@Column(name = "cc_order_url")
	private String callCenterOrderUrl;
	@Column(name = "headers")
	private String headers;
	@Column(name = "email")
	private String email;
	@Column(name = "station_url")
	private String stationUrl;
	@Column(name = "token_url")
	private String tokenUrl;
	@Column(name = "get_cart_url")
	private String getCartUrl;
	@Column(name = "cart_validate_url")
	private String cartValidateUrl;
	@Column(name = "order_confirm_url")
	private String orderConfirmUrl;
	@Column(name = "last_request_time")
	private Date lastUpdateTime;
	@Column(name = "order_initiate_url")
	private String orderInitiateUrl;


	public String getOutletUrl() {
		return outletUrl;
	}

	public void setOutletUrl(String outletUrl) {
		this.outletUrl = outletUrl;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getOrderUrl() {
		return orderUrl;
	}

	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}

	public Integer getOrderSendBeforeMins() {
		return orderSendBeforeMins;
	}

	public void setOrderSendBeforeMins(Integer orderSendBeforeMins) {
		this.orderSendBeforeMins = orderSendBeforeMins;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthValue() {
		return authValue;
	}

	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}

	public String getParserType() {
		return parserType;
	}

	public void setParserType(String parserType) {
		this.parserType = parserType;
	}

	public String getReverseOrderUrl() {
		return reverseOrderUrl;
	}

	public void setReverseOrderUrl(String reverseOrderUrl) {
		this.reverseOrderUrl = reverseOrderUrl;
	}

	public String getCallCenterOrderUrl() {
		return callCenterOrderUrl;
	}

	public void setCallCenterOrderUrl(String callCenterOrderUrl) {
		this.callCenterOrderUrl = callCenterOrderUrl;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStationUrl() {
		return stationUrl;
	}

	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getGetCartUrl() {
		return getCartUrl;
	}

	public void setGetCartUrl(String getCartUrl) {
		this.getCartUrl = getCartUrl;
	}

	public String getCartValidateUrl() {
		return cartValidateUrl;
	}

	public void setCartValidateUrl(String cartValidateUrl) {
		this.cartValidateUrl = cartValidateUrl;
	}

	public String getOrderConfirmUrl() {
		return orderConfirmUrl;
	}

	public void setOrderConfirmUrl(String orderConfirmUrl) {
		this.orderConfirmUrl = orderConfirmUrl;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getOrderInitiateUrl() {
		return orderInitiateUrl;
	}

	public void setOrderInitiateUrl(String orderInitiateUrl) {
		this.orderInitiateUrl = orderInitiateUrl;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	@Override
	public String toString() {
		return "AggregatorDataFetchDetail{" +
				", outletUrl='" + outletUrl + '\'' +
				", menuUrl='" + menuUrl + '\'' +
				", authKey='" + authKey + '\'' +
				", authValue='" + authValue + '\'' +
				", parserType='" + parserType + '\'' +
				'}';
	}

}
