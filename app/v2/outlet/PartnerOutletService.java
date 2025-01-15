package v2.outlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.model.AggregatorDataFetchDetail;
import common.resources.RequestResource;
import common.response.StationOutletResponse;
import org.apache.commons.collections4.MapUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Param;
import play.Logger;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;

public class PartnerOutletService {
	private final AsyncHttpClient httpClient = asyncHttpClient(config().setRequestTimeout(Duration.ofMillis(3000)));
	private final Logger.ALogger logger = Logger.of("v2.outletController.partnerOutletService");
	private final ObjectMapper mapper = new ObjectMapper();

	public CompletionStage<Optional<StationOutletResponse>> partnerStationOutlet(Long requestId, AggregatorDataFetchDetail aggregatorDataFetchDetail, Object body, Map<String, String> pathVariables) {
		String[] fullOutletURL = aggregatorDataFetchDetail.getOutletUrl().split(" ");
		String requestType = fullOutletURL[0];
		AtomicReference<String> url = new AtomicReference<>(fullOutletURL[1]);
		if (!MapUtils.isEmpty(pathVariables)) {
			pathVariables.forEach((name, value) -> url.set(url.get().replace(name, value)));
		}

		BoundRequestBuilder requestBuilder = null;

		switch (requestType) {
			case "GET":
				requestBuilder = httpClient.prepareGet(
						url.get());
				addHeaders(requestBuilder,
						Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
								aggregatorDataFetchDetail.getAuthValue()));
				addQueryParams(requestBuilder, body);
				return requestBuilder
						.execute()
						.toCompletableFuture()
						.exceptionally(throwable -> {
							logger.info("[{}] Aggregator Client failed to get any response ", requestId);
							return null;
						}).thenApplyAsync(response -> {
							if (response == null || response.getStatusCode() >= 400) {
								logger.info(
										"[{}] Got failure response from Aggregator API with status code {} for url {}",
										requestId,
										response != null ? response.getStatusCode() : 0,
										url.get());
								return Optional.empty();
							}
							try {
								logger.info(
										"[{}] Received outlet list Response: {} ", requestId,
										response.getResponseBody());
								StationOutletResponse responseResource = mapper.readValue(
										response.getResponseBody(),
										StationOutletResponse.class);
								return Optional.of(responseResource);
							} catch (Exception e) {
								logger.error("[{}] Exception while parsing Aggregator Response: {}",
										requestId,
										e.getMessage());
								return Optional.empty();
							}
						});
			case "POST":
				GsonBuilder gsonBuilder = new GsonBuilder();
				String jsonString = gsonBuilder.create().toJson(body);
				requestBuilder = httpClient.preparePost(url.get());
				addHeaders(requestBuilder,
						Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
								aggregatorDataFetchDetail.getAuthValue()));
				addQueryParams(requestBuilder, body);
				return requestBuilder
						.setBody(jsonString)
						.execute()
						.toCompletableFuture()
						.exceptionally(throwable -> {
							logger.info("[{}] Aggregator Client failed to get any response ", requestId);
							return null;
						}).thenApplyAsync(response -> {
							if (response == null || response.getStatusCode() >= 400) {
								logger.info(
										"[{}] Got failure response from Aggregator API with status code {} for url {}",
										requestId,
										response != null ? response.getStatusCode() : 0,
										url.get());
								return Optional.empty();
							}
							try {
								logger.info(
										"[{}] Received outlet list Response: {} ", requestId,
										response.getResponseBody());
								StationOutletResponse responseResource = mapper.readValue(
										response.getResponseBody(),
										StationOutletResponse.class);
								return Optional.of(responseResource);
							} catch (Exception e) {
								logger.error("[{}] Exception while parsing Aggregator Response: {}",
										requestId,
										e.getMessage());
								return Optional.empty();
							}
						});
			default:
				return supplyAsync(() -> {
					logger.error("[{}] Unrecognized HTTP verb for aggregator call", requestId);
					return Optional.empty();
				});
		}

	}

	private void addQueryParams(BoundRequestBuilder requestBuilder, Object body) {
		logger.info("body " + body.toString());
		if (body instanceof RequestResource requestResource) {
			logger.info("page " + requestResource.getPage());
			requestBuilder.addQueryParam("stationCode", requestResource.getStationCode());
			requestBuilder.addQueryParam("date", requestResource.getDate());
			requestBuilder.addQueryParam("time", requestResource.getTime());
			requestBuilder.addQueryParam("requestId", requestResource.getRequestId());
			requestBuilder.addQueryParam("size", String.valueOf(requestResource.getSize()));
			requestBuilder.addQueryParam("page", String.valueOf(requestResource.getPage()));
		}
	}

	private void addHeaders(BoundRequestBuilder requestBuilder, Map<String, String> headers) {
		headers.forEach(requestBuilder::addHeader);
	}

}
