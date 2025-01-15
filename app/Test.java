//public class Test {
//}
//package common.aggregator.api.partial.integration;
//
//import static java.util.concurrent.CompletableFuture.supplyAsync;
//import static org.asynchttpclient.Dsl.asyncHttpClient;
//import static org.asynchttpclient.Dsl.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.GsonBuilder;
//import common.aggregator.model.AggregatorDataFetchDetail;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.CompletionStage;
//import java.util.concurrent.atomic.AtomicReference;
//import org.apache.commons.collections4.MapUtils;
//import org.asynchttpclient.AsyncHttpClient;
//import org.asynchttpclient.BoundRequestBuilder;
//import play.Logger.ALogger;
//
//public class AggregatorIntegrationClient {
//
//	private static final ALogger logger = play.Logger.of("application.AggregatorIntegrationClient");
//	private final AsyncHttpClient httpClient = asyncHttpClient(config().setRequestTimeout(3000));
//	private final ObjectMapper mapper = new ObjectMapper();
//
//	public CompletionStage<Optional<AggregatorStationOutletIntegrationResponse>> getStationOutlet(
//			AggregatorDataFetchDetail aggregatorDataFetchDetail, Map<String, String> pathVariables,
//			Object body, long requestId) {
//		logger.info("[{}] getting outlet list", requestId);
//		String[] fullOutletURL = aggregatorDataFetchDetail.getOutletUrl().split(" ");
//		logger.info("[{}] endpoint for outlet list: {} ", requestId, fullOutletURL);
//		String verb = fullOutletURL[0];
//		AtomicReference<String> url = new AtomicReference<>(fullOutletURL[1]);
//		if (MapUtils.isNotEmpty(pathVariables)) {
//			pathVariables.forEach((name, value) -> url.set(url.get().replace(name, value)));
//		}
//		BoundRequestBuilder requestBuilder = null;
//		switch (verb) {
//			case "GET":
//				requestBuilder = httpClient.prepareGet(
//						url.get());
//				addHeaders(requestBuilder,
//						Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
//								aggregatorDataFetchDetail.getAuthValue()));
//				addQueryParams(requestBuilder, body);
//				return requestBuilder
//						.execute()
//						.toCompletableFuture()
//						.exceptionally(throwable -> {
//							logger.info("[{}] Aggregator Client failed to get any response ", requestId);
//							return null;
//						}).thenApplyAsync(response -> {
//							if (response == null || response.getStatusCode() >= 400) {
//								logger.info(
//										"[{}] Got failure response from Aggregator API with status code {} for url {}",
//										requestId,
//										response != null ? response.getStatusCode() : 0,
////										url);
//								return Optional.empty();
//							}
//							try {
//								logger.info(
//										"[{}] Received outlet list Response: {} ", requestId,
//										response.getResponseBody());
//								AggregatorStationOutletIntegrationResponse responseResource = mapper.readValue(
//										response.getResponseBody(),
//										AggregatorStationOutletIntegrationResponse.class);
//								return Optional.of(responseResource);
//							} catch (Exception e) {
//								logger.error("[{}] Exception while parsing Aggregator Response: {}",
//										requestId,
//										e.getMessage());
//								return Optional.empty();
//							}
//						});
//			case "POST":
//				GsonBuilder gsonBuilder = new GsonBuilder();
//				String jsonString = gsonBuilder.create().toJson(body);
//				requestBuilder = httpClient.preparePost(
//						url.get());
//				addHeaders(requestBuilder,
//						Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
//								aggregatorDataFetchDetail.getAuthValue()));
//				return requestBuilder
//						.setBody(jsonString)
//						.execute()
//						.toCompletableFuture()
//						.exceptionally(throwable -> {
//							logger.info("[{}] Aggregator Client failed to get any response ", requestId);
//							return null;
//						}).thenApplyAsync(response -> {
//							if (response == null || response.getStatusCode() >= 400) {
//								logger.info(
//										"[{}] Got failure response from Aggregator API with status code {} for url {}",
//										requestId,
//										response != null ? response.getStatusCode() : 0,
//										url);
//								return Optional.empty();
//							}
//							try {
//								logger.info(
//										"[{}] Received outlet list Response: {} ", requestId,
//										response.getResponseBody());
//								AggregatorStationOutletIntegrationResponse responseResource = mapper.readValue(
//										response.getResponseBody(),
//										AggregatorStationOutletIntegrationResponse.class);
//								return Optional.of(responseResource);
//							} catch (Exception e) {
//								logger.error("[{}] Exception while parsing Aggregator Response: {}",
//										requestId,
//										e.getMessage());
//								return Optional.empty();
//							}
//						});
//			default:
//				return supplyAsync(() -> {
//					logger.error("[{}] Unrecognized HTTP verb for aggregator call", requestId);
//					return Optional.empty();
//				});
//		}
//	}
//
//	public CompletionStage<Optional<AggregatorMenuResponseResource>> getActiveMenuItems(
//			AggregatorDataFetchDetail aggregatorDataFetchDetail,
//			Object body, long requestId, Map<String, String> pathVariables) {
//		String[] fullOutletURL = aggregatorDataFetchDetail.getMenuUrl().split(" ");
//		String verb = fullOutletURL[0];
//		AtomicReference<String> url = new AtomicReference<>(fullOutletURL[1]);
//		if (MapUtils.isNotEmpty(pathVariables)) {
//			pathVariables.forEach((name, value) -> url.set(url.get().replace(name, value)));
//		}
//		BoundRequestBuilder requestBuilder = null;
//		switch (verb) {
//			case "GET":
//				requestBuilder = httpClient.prepareGet(
//						url.get());
//				addHeaders(requestBuilder,
//						Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
//								aggregatorDataFetchDetail.getAuthValue()));
//				addQueryParams(requestBuilder, body);
//				return requestBuilder
//						.execute()
//						.toCompletableFuture()
//						.exceptionally(throwable -> {
//							logger.info("[{}] Aggregator Client failed to get any response ", requestId);
//							return null;
//						}).thenApplyAsync(response -> {
//							if (response == null || response.getStatusCode() >= 400) {
//								logger.info(
//										"[{}] Got failure response from Aggregator API with status code {} for url {}",
//										requestId,
//										response != null ? response.getStatusCode() : 0,
//										url);
//								return Optional.empty();
//							}
//							try {
//								AggregatorMenuResponseResource responseResource = mapper.readValue(
//										response.getResponseBody(),
//										AggregatorMenuResponseResource.class);
//								logger.info("[{}] Client Menu Response URL{}, with response: {}  ",
//										requestId, url,
//										responseResource);
//								return Optional.of(responseResource);
//							} catch (Exception e) {
//								logger.error("[{}] Exception while parsing Aggregator Response -> {} for url {}",
//										requestId,
//										e.getMessage(), url);
//								return Optional.empty();
//							}
//						});
//			case "POST":
//				GsonBuilder gsonBuilder = new GsonBuilder();
//				String jsonString = gsonBuilder.create().toJson(body);
//				requestBuilder = httpClient.preparePost(
//						url.get());
//				addHeaders(requestBuilder,
//						Map.of("content-type", "application/json", aggregatorDataFetchDetail.getAuthKey(),
//								aggregatorDataFetchDetail.getAuthValue()));
//				return requestBuilder
//						.setBody(jsonString)
//						.execute()
//						.toCompletableFuture()
//						.exceptionally(throwable -> {
//							logger.info("[{}] Aggregator Client failed to get any response ", requestId);
//							return null;
//						}).thenApplyAsync(response -> {
//							if (response == null || response.getStatusCode() >= 400) {
//								logger.info(
//										"[{}] Got failure response from Aggregator API with status code {} for url {}",
//										requestId,
//										response != null ? response.getStatusCode() : 0,
//										url);
//								return Optional.empty();
//							}
//							try {
//								AggregatorMenuResponseResource responseResource = mapper.readValue(
//										response.getResponseBody(),
//										AggregatorMenuResponseResource.class);
//								logger.info("[{}] Client Menu Response URL{}, with response: {}  ",
//										requestId, url,
//										responseResource);
//								return Optional.of(responseResource);
//							} catch (Exception e) {
//								logger.error("[{}] Exception while parsing Aggregator Response -> {} for url {}",
//										requestId,
//										e.getMessage(), url);
//								return Optional.empty();
//							}
//						});
//			default:
//				return supplyAsync(() -> {
//					logger.error("[{}] Unrecognized HTTP verb for aggregator call", requestId);
//					return Optional.empty();
//				});
//		}
//
//	}
//
//	private void addQueryParams(BoundRequestBuilder requestBuilder, Object body) {
//		if (body instanceof PartialAPIAggregatorOutletListRequestResource requestResource) {
//			requestBuilder.addQueryParam("stationCode", requestResource.stationCode());
//			requestBuilder.addQueryParam("date", requestResource.date());
//			requestBuilder.addQueryParam("time", requestResource.time());
//			requestBuilder.addQueryParam("requestId", requestResource.requestId());
//			requestBuilder.addQueryParam("size", String.valueOf(requestResource.size()));
//			requestBuilder.addQueryParam("page", String.valueOf(requestResource.page()));
//		} else if (body instanceof PartialAPIAggregatorMenuListRequestResource requestResource) {
//			requestBuilder.addQueryParam("outletId", requestResource.outletId());
//			requestBuilder.addQueryParam("stationCode", requestResource.stationCode());
//			requestBuilder.addQueryParam("date", requestResource.date());
//			requestBuilder.addQueryParam("time", requestResource.time());
//		}
//	}
//
//	private void addHeaders(BoundRequestBuilder requestBuilder, Map<String, String> headers) {
//		headers.forEach(requestBuilder::addHeader);
//	}
//}