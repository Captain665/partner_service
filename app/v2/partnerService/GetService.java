package v2.partnerService;

import common.model.AggregatorDataFetchDetail;
import common.resources.RequestResource;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import play.Logger;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;

public class GetService {

	private final Logger.ALogger logger = Logger.of("v2.partnerService.getService");
	private final AsyncHttpClient httpClient = asyncHttpClient(config().setRequestTimeout(Duration.ofMillis(3000)));

	public CompletionStage<Optional<?>> getInfo(Long requestId, AggregatorDataFetchDetail aggregatorDataFetchDetail, Object body, AtomicReference<String> url) {

		BoundRequestBuilder requestBuilder = null;
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
						logger.info("[{}] response is : {}", requestId, response.getResponseBody());
						return Optional.empty();
					}
					try {
						logger.info(
								"[{}] Received Aggregator Response: {} ", requestId,
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
