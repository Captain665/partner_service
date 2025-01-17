package v2.partnerService;

import com.google.gson.GsonBuilder;
import common.model.AggregatorDataFetchDetail;
import common.resources.RequestResource;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import play.Logger;
import play.mvc.Http;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

import static org.asynchttpclient.Dsl.*;

public class PostService {

	private final Logger.ALogger logger = Logger.of("v2.partnerService.postService");
	private final AsyncHttpClient httpClient = asyncHttpClient(config().setRequestTimeout(Duration.ofMillis(5000)));

	public CompletionStage<Optional<?>> getInfo(Http.Request request, AggregatorDataFetchDetail aggregatorDataFetchDetail, Object body, AtomicReference<String> url, Boolean isJson) {
		BoundRequestBuilder requestBuilder = null;

		GsonBuilder gsonBuilder = new GsonBuilder();
		String jsonString = isJson ? body.toString() : gsonBuilder.create().toJson(body);
		requestBuilder = httpClient.preparePost(url.get());

		if (aggregatorDataFetchDetail.getVendorId() == 1190) {
			addHeaders(requestBuilder,
					Map.of("content-type", "application/json",
							aggregatorDataFetchDetail.getAuthKey(), aggregatorDataFetchDetail.getAuthValue(),
							"storeId", request.header("storeId").isPresent() ? request.header("storeId").get() : "",
							"client_token", request.header("client_token").isPresent() ? request.header("client_token").get() : "",
							"client_type", request.header("client_type").isPresent() ? request.header("client_type").get() : "",
							"deliveryType", request.header("deliveryType").isPresent() ? request.header("deliveryType").get() : "",
							"userId", request.header("userId").isPresent() ? request.header("userId").get() : ""
					));
		} else {
			addHeaders(requestBuilder,
					Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
							aggregatorDataFetchDetail.getAuthValue()));
		}
		addQueryParams(requestBuilder, body);

		logger.info("url is " + url.get());

		return requestBuilder
				.setBody(jsonString)
				.execute()
				.toCompletableFuture()
				.exceptionally(throwable -> {
					logger.info("[{}] Aggregator Client failed to get any response ", request.id());
					logger.info("exception " + throwable);
					return null;
				}).thenApplyAsync(response -> {
					logger.info("response " + response);
					if ((response == null || response.getStatusCode() >= 400) && aggregatorDataFetchDetail.getVendorId() != 1190) {
						logger.info(
								"[{}] Got failure response from Aggregator API with status code {} for url {}",
								request.id(),
								response != null ? response.getStatusCode() : 0,
								url.get());
						return Optional.empty();
					}
					try {
						logger.info(
								"[{}] Received Aggregator Response: {} ", request.id(),
								response.getResponseBody());
						return Optional.of(response.getResponseBody());
					} catch (Exception e) {
						logger.error("[{}] Exception while parsing Aggregator Response: {}",
								request.id(),
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
