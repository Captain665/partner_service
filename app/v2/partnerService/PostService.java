package v2.partnerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import common.model.AggregatorDataFetchDetail;
import common.resources.RequestResource;
import common.response.StationOutletResponse;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import play.Logger;
import play.libs.Json;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;

public class PostService {

	private final Logger.ALogger logger = Logger.of("v2.partnerService.postService");
	private final AsyncHttpClient httpClient = asyncHttpClient(config().setRequestTimeout(Duration.ofMillis(3000)));

	public CompletionStage<Optional<?>> getInfo(Long requestId, AggregatorDataFetchDetail aggregatorDataFetchDetail, Object body, AtomicReference<String> url, Boolean isJson) {
		BoundRequestBuilder requestBuilder = null;

		GsonBuilder gsonBuilder = new GsonBuilder();
		String jsonString = isJson ? body.toString() : gsonBuilder.create().toJson(body);
		requestBuilder = httpClient.preparePost(url.get());
		addHeaders(requestBuilder,
				Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
						aggregatorDataFetchDetail.getAuthValue()));
		addQueryParams(requestBuilder, body);

		logger.info("url " + url.get());
		return requestBuilder
				.setBody(jsonString)
				.execute()
				.toCompletableFuture()
				.exceptionally(throwable -> {
					logger.info("[{}] Aggregator Client failed to get any response ", requestId);
					return null;
				}).thenApplyAsync(response -> {
					logger.info("response -> " + response.getResponseBody());
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
						return Optional.of(response.getResponseBody());
					} catch (Exception e) {
						logger.error("[{}] Exception while parsing Aggregator Response: {}",
								requestId,
								e.getMessage());
						return Optional.empty();
					}
				});
	}

	private void addQueryParams(BoundRequestBuilder requestBuilder, Object body) {
		if (body instanceof RequestResource requestResource) {
			logger.info("query param is also running ");
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
