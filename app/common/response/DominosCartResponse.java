package common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

//@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DominosCartResponse {
	public String status;
	public long cartId;
	public List<Object> invalidItems;
	public List<Object> validItems;
	public BigDecimal price;
	public BigDecimal discount;
	public BigDecimal netPrice;
	public BigDecimal netPriceWithAddCharge;
	public Object tax;
	public BigInteger priceScale;
	public String priceRoundMode;
	public List<Object> taxes;
	public BigDecimal taxAmount;
	public Object deliveryCharge;
	public List<Object> cartAddChargeDTO;
	public BigDecimal priceWithoutDiscount;
	public Object taxWithoutDiscount;
	public BigDecimal totalPriceWithoutDiscount;
	public Boolean nextGenFlagEnabled;
	public Object comboMapping;
	public BigInteger displayPriceWithoutDiscount;
	public BigInteger displayNetPrice;
	public BigInteger displayNetPriceWithAddCharges;
	public BigDecimal deliveryCharges;
	public String deliveryChargeDescription;


	public DominosCartResponse() {
	}

	public DominosCartResponse(String status, long cartId, List<Object> invalidItems, List<Object> validItems, BigDecimal price, BigDecimal discount, BigDecimal netPrice, BigDecimal netPriceWithAddCharge, Object tax, BigInteger priceScale, String priceRoundMode, List<Object> taxes, BigDecimal taxAmount, Object deliveryCharge, List<Object> cartAddChargeDTO, BigDecimal priceWithoutDiscount, Object taxWithoutDiscount, BigDecimal totalPriceWithoutDiscount, Boolean nextGenFlagEnabled, Object comboMapping, BigInteger displayPriceWithoutDiscount, BigInteger displayNetPrice, BigInteger displayNetPriceWithAddCharges, BigDecimal deliveryCharges, String deliveryChargeDescription) {
		this.status = status;
		this.cartId = cartId;
		this.invalidItems = invalidItems;
		this.validItems = validItems;
		this.price = price;
		this.discount = discount;
		this.netPrice = netPrice;
		this.netPriceWithAddCharge = netPriceWithAddCharge;
		this.tax = tax;
		this.priceScale = priceScale;
		this.priceRoundMode = priceRoundMode;
		this.taxes = taxes;
		this.taxAmount = taxAmount;
		this.deliveryCharge = deliveryCharge;
		this.cartAddChargeDTO = cartAddChargeDTO;
		this.priceWithoutDiscount = priceWithoutDiscount;
		this.taxWithoutDiscount = taxWithoutDiscount;
		this.totalPriceWithoutDiscount = totalPriceWithoutDiscount;
		this.nextGenFlagEnabled = nextGenFlagEnabled;
		this.comboMapping = comboMapping;
		this.displayPriceWithoutDiscount = displayPriceWithoutDiscount;
		this.displayNetPrice = displayNetPrice;
		this.displayNetPriceWithAddCharges = displayNetPriceWithAddCharges;
		this.deliveryCharges = deliveryCharges;
		this.deliveryChargeDescription = deliveryChargeDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public List<Object> getInvalidItems() {
		return invalidItems;
	}

	public void setInvalidItems(List<Object> invalidItems) {
		this.invalidItems = invalidItems;
	}

	public List<Object> getValidItems() {
		return validItems;
	}

	public void setValidItems(List<Object> validItems) {
		this.validItems = validItems;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}

	public BigDecimal getNetPriceWithAddCharge() {
		return netPriceWithAddCharge;
	}

	public void setNetPriceWithAddCharge(BigDecimal netPriceWithAddCharge) {
		this.netPriceWithAddCharge = netPriceWithAddCharge;
	}

	public Object getTax() {
		return tax;
	}

	public void setTax(Object tax) {
		this.tax = tax;
	}

	public BigInteger getPriceScale() {
		return priceScale;
	}

	public void setPriceScale(BigInteger priceScale) {
		this.priceScale = priceScale;
	}

	public String getPriceRoundMode() {
		return priceRoundMode;
	}

	public void setPriceRoundMode(String priceRoundMode) {
		this.priceRoundMode = priceRoundMode;
	}

	public List<Object> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Object> taxes) {
		this.taxes = taxes;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Object getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(Object deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public List<Object> getCartAddChargeDTO() {
		return cartAddChargeDTO;
	}

	public void setCartAddChargeDTO(List<Object> cartAddChargeDTO) {
		this.cartAddChargeDTO = cartAddChargeDTO;
	}

	public BigDecimal getPriceWithoutDiscount() {
		return priceWithoutDiscount;
	}

	public void setPriceWithoutDiscount(BigDecimal priceWithoutDiscount) {
		this.priceWithoutDiscount = priceWithoutDiscount;
	}

	public Object getTaxWithoutDiscount() {
		return taxWithoutDiscount;
	}

	public void setTaxWithoutDiscount(Object taxWithoutDiscount) {
		this.taxWithoutDiscount = taxWithoutDiscount;
	}

	public BigDecimal getTotalPriceWithoutDiscount() {
		return totalPriceWithoutDiscount;
	}

	public void setTotalPriceWithoutDiscount(BigDecimal totalPriceWithoutDiscount) {
		this.totalPriceWithoutDiscount = totalPriceWithoutDiscount;
	}

	public Boolean getNextGenFlagEnabled() {
		return nextGenFlagEnabled;
	}

	public void setNextGenFlagEnabled(Boolean nextGenFlagEnabled) {
		this.nextGenFlagEnabled = nextGenFlagEnabled;
	}

	public Object getComboMapping() {
		return comboMapping;
	}

	public void setComboMapping(Object comboMapping) {
		this.comboMapping = comboMapping;
	}

	public BigInteger getDisplayPriceWithoutDiscount() {
		return displayPriceWithoutDiscount;
	}

	public void setDisplayPriceWithoutDiscount(BigInteger displayPriceWithoutDiscount) {
		this.displayPriceWithoutDiscount = displayPriceWithoutDiscount;
	}

	public BigInteger getDisplayNetPrice() {
		return displayNetPrice;
	}

	public void setDisplayNetPrice(BigInteger displayNetPrice) {
		this.displayNetPrice = displayNetPrice;
	}

	public BigInteger getDisplayNetPriceWithAddCharges() {
		return displayNetPriceWithAddCharges;
	}

	public void setDisplayNetPriceWithAddCharges(BigInteger displayNetPriceWithAddCharges) {
		this.displayNetPriceWithAddCharges = displayNetPriceWithAddCharges;
	}

	public BigDecimal getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(BigDecimal deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public String getDeliveryChargeDescription() {
		return deliveryChargeDescription;
	}

	public void setDeliveryChargeDescription(String deliveryChargeDescription) {
		this.deliveryChargeDescription = deliveryChargeDescription;
	}

	@Override
	public String toString() {
		return "DominosCartResponse{" +
				"status='" + status + '\'' +
				", cartId=" + cartId +
				", invalidItems=" + invalidItems +
				", validItems=" + validItems +
				", price=" + price +
				", discount=" + discount +
				", netPrice=" + netPrice +
				", netPriceWithAddCharge=" + netPriceWithAddCharge +
				", tax=" + tax +
				", priceScale=" + priceScale +
				", priceRoundMode='" + priceRoundMode + '\'' +
				", taxes=" + taxes +
				", taxAmount=" + taxAmount +
				", deliveryCharge=" + deliveryCharge +
				", cartAddChargeDTO=" + cartAddChargeDTO +
				", priceWithoutDiscount=" + priceWithoutDiscount +
				", taxWithoutDiscount=" + taxWithoutDiscount +
				", totalPriceWithoutDiscount=" + totalPriceWithoutDiscount +
				", nextGenFlagEnabled=" + nextGenFlagEnabled +
				", comboMapping=" + comboMapping +
				", displayPriceWithoutDiscount=" + displayPriceWithoutDiscount +
				", displayNetPrice=" + displayNetPrice +
				", displayNetPriceWithAddCharges=" + displayNetPriceWithAddCharges +
				", deliveryCharges=" + deliveryCharges +
				", deliveryChargeDescription='" + deliveryChargeDescription + '\'' +
				'}';
	}
}
