package common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuResponse {
	public Boolean validateCart;
	public List<Object> categories;
	public Long outletId;
	public Long vendorId;
	public Object error;

	public MenuResponse() {
	}

	public MenuResponse(Boolean validateCart, List<Object> categories, Long outletId, Long vendorId, Object error) {
		this.validateCart = validateCart;
		this.categories = categories;
		this.outletId = outletId;
		this.vendorId = vendorId;
		this.error = error;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public Long getOutletId() {
		return outletId;
	}

	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Boolean getValidateCart() {
		return validateCart;
	}

	public void setValidateCart(Boolean validateCart) {
		this.validateCart = validateCart;
	}

	public List<Object> getCategories() {
		return categories;
	}

	public void setCategories(List<Object> categories) {
		this.categories = categories;
	}
}
