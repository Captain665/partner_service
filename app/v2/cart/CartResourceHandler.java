package v2.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.log.Log;
import common.model.AggregatorDataFetchDetail;
import common.response.CartResponse;
import common.response.DominosCartResponse;
import common.response.DominosRefreshTokenResponse;
import jakarta.inject.Inject;
import org.apache.commons.collections4.MapUtils;
import play.Logger;
import play.mvc.Http;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;
import v2.partnerService.GetService;
import v2.partnerService.PostService;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class CartResourceHandler {

	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;
	private final PostService postService;
	private final GetService getService;
	private final ObjectMapper mapper = new ObjectMapper();
	private final Logger.ALogger logger = Logger.of("v1.cartResourceHandler");

	@Inject
	public CartResourceHandler(AggregatorDataFetchRepository aggregatorDataFetchRepository, PostService postService, GetService getService) {
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
		this.postService = postService;
		this.getService = getService;
	}

	public CompletionStage<?> cartValidateByPartner(Long partnerId, Http.Request request, Object body, Map<String, String> pathVariables) {
		return aggregatorDataFetchRepository.getData(partnerId)
				.thenComposeAsync(aggregatorDataFetchDetail -> {
					AtomicReference<String> url = new AtomicReference<>(aggregatorDataFetchDetail.getCartValidateUrl());

					if (!MapUtils.isEmpty(pathVariables)) {
						pathVariables.forEach((name, value) -> url.set(url.get().replace(name, value)));
					}
					return getDominoRefreshToken(aggregatorDataFetchDetail, request.id())
							.thenComposeAsync(refreshToken ->
									postService.getInfo(request, aggregatorDataFetchDetail, body, url, true, refreshToken))
							.thenApplyAsync(partnerResponse -> {
								if (partnerResponse.isPresent()) {
									try {
										if (aggregatorDataFetchDetail.getVendorId() == 1190) {
											return mapper.readValue(partnerResponse.get().toString(), DominosCartResponse.class);
										}
										return mapper.readValue(partnerResponse.get().toString(), CartResponse.class);
									} catch (Exception e) {
										return null;
									}
								}
								return null;
							});
				});
	}

	public CompletionStage<Optional<String>> getDominoRefreshToken(AggregatorDataFetchDetail aggregatorDataFetchDetail, long requestId) {
		if (aggregatorDataFetchDetail.getVendorId() == 1190) {
			logger.info("[" + requestId + "] " + "getting domino's refresh token");
			AtomicReference<String> url = new AtomicReference<>(aggregatorDataFetchDetail.getTokenUrl());
			return getService.getInfo(requestId, aggregatorDataFetchDetail, null, url)
					.thenApplyAsync(response -> {
						if (response.isPresent()) {
							try {
								return Optional.of(mapper.readValue(response.get().toString(), DominosRefreshTokenResponse.class).client_token);
							} catch (JsonProcessingException e) {
								return Optional.empty();
							}
						}
						return Optional.empty();
					});
		}
		return supplyAsync(Optional::empty);
	}
}
