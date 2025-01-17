package v2.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.response.CartResponse;
import jakarta.inject.Inject;
import org.apache.commons.collections4.MapUtils;
import play.Logger;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;
import v2.partnerService.PostService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

public class CartResourceHandler {

	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;
	private final PostService postService;
	private final ObjectMapper mapper = new ObjectMapper();

	@Inject
	public CartResourceHandler(AggregatorDataFetchRepository aggregatorDataFetchRepository, PostService postService) {
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
		this.postService = postService;
	}

	public CompletionStage<CartResponse> cartValidateByPartner(Long partnerId, Long requestId, Object body, Map<String, String> pathVariables) {
		return aggregatorDataFetchRepository.getData(partnerId)
				.thenComposeAsync(aggregatorDataFetchDetail -> {
					AtomicReference<String> url = new AtomicReference<>(aggregatorDataFetchDetail.getCartValidateUrl());

					if (!MapUtils.isEmpty(pathVariables)) {
						pathVariables.forEach((name, value) -> url.set(url.get().replace(name, value)));
					}

					return postService.getInfo(requestId, aggregatorDataFetchDetail, body, url, true)
							.thenApplyAsync(partnerResponse -> {
								if (partnerResponse.isPresent()) {
									try {
										return mapper.readValue(partnerResponse.get().toString(), CartResponse.class);
									} catch (IOException e) {
										return null;
									}
								}
								return null;
							});
				});
	}
}
