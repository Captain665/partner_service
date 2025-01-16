package v2.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.response.CartResponse;
import jakarta.inject.Inject;
import play.Logger;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;
import v2.partnerService.PostService;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

public class CartResourceHandler {
	private final Logger.ALogger logger = Logger.of("v2.outletController");

	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;
	private final PostService postService;
	private final ObjectMapper mapper = new ObjectMapper();

	@Inject
	public CartResourceHandler(AggregatorDataFetchRepository aggregatorDataFetchRepository, PostService postService) {
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
		this.postService = postService;
	}

	public CompletionStage<CartResponse> cartValidateByPartner(Long partnerId, Long requestId, Object body) {
		return aggregatorDataFetchRepository.getData(partnerId)
				.thenComposeAsync(aggregatorDataFetchDetail -> {
					AtomicReference<String> url = new AtomicReference<>(aggregatorDataFetchDetail.getCartValidateUrl());
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
