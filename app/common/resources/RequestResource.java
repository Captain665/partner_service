package common.resources;

import play.mvc.QueryStringBindable;

import java.util.Map;
import java.util.Optional;

public class RequestResource implements QueryStringBindable<RequestResource> {
	public String stationCode;
	public String date;
	public String time;
	public String requestId;
	public Integer size;
	public Integer page;
	public String outletId;
	public String partnerName;


	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	@Override
	public Optional<RequestResource> bind(String key, Map<String, String[]> data) {
		try {

			RequestResource resource = new RequestResource();

			resource.setStationCode(getFirstValue(data, "stationCode"));
			resource.setDate(getFirstValue(data, "date"));
			resource.setTime(getFirstValue(data, "time"));
			resource.setRequestId(getFirstValue(data, "requestId"));
			resource.setSize(parseInteger(getFirstValue(data, "size") == null ? "20" : getFirstValue(data, "size")));
			resource.setPage(parseInteger(getFirstValue(data, "page") == null ? "1" : getFirstValue(data, "page")));
			resource.setOutletId(getFirstValue(data, "outletId"));
			resource.setPartnerName(getFirstValue(data, "partnerName"));

			return Optional.of(resource);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public String unbind(String key) {
		return String.format(
				"stationCode=%s&date=%s&time=%s&requestId=%s&size=%d&page=%d&outletId=%s&partnerName=%s",
				stationCode, date, time, requestId, size, page, outletId, partnerName
		);
	}

	@Override
	public String javascriptUnbind() {
		return unbind("");
	}

	private String getFirstValue(Map<String, String[]> data, String key) {
		String[] values = data.get(key);
		return (values != null && values.length > 0) ? values[0] : null;
	}

	private Integer parseInteger(String value) {
		try {
			return value != null ? Integer.parseInt(value) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "RequestResource{" +
				"stationCode='" + stationCode + '\'' +
				", date='" + date + '\'' +
				", time='" + time + '\'' +
				", requestId='" + requestId + '\'' +
				", size=" + size +
				", page=" + page +
				", outletId='" + outletId + '\'' +
				", partnerName='" + partnerName + '\'' +
				'}';
	}
}
