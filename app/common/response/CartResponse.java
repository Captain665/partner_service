package common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CartResponse {
	public Object cart;
	public String coupon;
	public List<Object> promotions;
	public Object error;

	public CartResponse() {
	}

	public CartResponse(Object cart, String coupon, List<Object> promotions, Object error) {
		this.cart = cart;
		this.coupon = coupon;
		this.promotions = promotions;
		this.error = error;
	}

	public Object getCart() {
		return cart;
	}

	public void setCart(Object cart) {
		this.cart = cart;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public List<Object> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Object> promotions) {
		this.promotions = promotions;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}


	@Override
	public String toString() {
		return "CartResponse{" +
				"cart=" + cart +
				", coupon='" + coupon + '\'' +
				", promotions=" + promotions +
				", error=" + error +
				'}';
	}
}
