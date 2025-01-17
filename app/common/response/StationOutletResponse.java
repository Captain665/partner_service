package common.response;


import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationOutletResponse {
	public String[] filters;
	public List<Object> outlets;
	public Boolean hasVendorPromotion;
	public Boolean hasMore;
	public Integer page;
	public Integer size;
	public String requestId;
	public Object error;


	public StationOutletResponse() {
	}

	public StationOutletResponse(String[] filters, List<Object> outlets, Boolean hasVendorPromotion, Boolean hasMore, Integer page, Integer size, String requestId, Object error) {
		this.filters = filters;
		this.outlets = outlets;
		this.hasVendorPromotion = hasVendorPromotion;
		this.hasMore = hasMore;
		this.page = page;
		this.size = size;
		this.requestId = requestId;
		this.error = error;
	}

	public String[] getFilters() {
		return filters;
	}

	public void setFilters(String[] filters) {
		this.filters = filters;
	}

	public List<Object> getOutlets() {
		return outlets;
	}

	public void setOutlets(List<Object> outlets) {
		this.outlets = outlets;
	}

	public Boolean getHasVendorPromotion() {
		return hasVendorPromotion;
	}

	public void setHasVendorPromotion(Boolean hasVendorPromotion) {
		this.hasVendorPromotion = hasVendorPromotion;
	}

	public Boolean getHasMore() {
		return hasMore;
	}

	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "StationOutletResponse{" +
				"filters=" + Arrays.toString(filters) +
				", outlets=" + outlets +
				", hasVendorPromotion=" + hasVendorPromotion +
				", hasMore=" + hasMore +
				", page=" + page +
				", size=" + size +
				'}';
	}
}
