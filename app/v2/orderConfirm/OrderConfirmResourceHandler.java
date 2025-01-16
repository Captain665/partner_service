package v2.orderConfirm;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.response.CartResponse;
import jakarta.inject.Inject;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;
import v2.partnerService.PostService;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

public class OrderConfirmResourceHandler {

	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;
	private final PostService postService;
	private final ObjectMapper mapper = new ObjectMapper();

	@Inject
	public OrderConfirmResourceHandler(AggregatorDataFetchRepository aggregatorDataFetchRepository, PostService postService) {
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
		this.postService = postService;
	}


	public CompletionStage<CartResponse> orderConfirmByPartner(Long partnerId, Long requestId, Object body) {

		return aggregatorDataFetchRepository.getData(partnerId)
				.thenComposeAsync(aggregatorDataFetchDetail -> {
					AtomicReference<String> url = new AtomicReference<>(aggregatorDataFetchDetail.getOrderConfirmUrl());
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
