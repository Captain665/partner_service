package v2.orderConfirm;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.response.CartResponse;
import common.response.DominosOrderConfirmResponse;
import jakarta.inject.Inject;
import org.apache.commons.collections4.MapUtils;
import play.mvc.Http;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;
import v2.cart.CartResourceHandler;
import v2.partnerService.PostService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

public class OrderConfirmResourceHandler {

	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;
	private final PostService postService;
	private final ObjectMapper mapper = new ObjectMapper();
	private final CartResourceHandler cartResourceHandler;

	@Inject
	public OrderConfirmResourceHandler(AggregatorDataFetchRepository aggregatorDataFetchRepository, PostService postService, CartResourceHandler cartResourceHandler) {
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
		this.postService = postService;
		this.cartResourceHandler = cartResourceHandler;
	}


	public CompletionStage<?> orderConfirmByPartner(Long partnerId, Http.Request request, Object body) {

		return aggregatorDataFetchRepository.getData(partnerId)
				.thenComposeAsync(aggregatorDataFetchDetail -> {

					AtomicReference<String> url = new AtomicReference<>(aggregatorDataFetchDetail.getOrderConfirmUrl());

					return cartResourceHandler.getDominoRefreshToken(aggregatorDataFetchDetail, request.id())
							.thenComposeAsync(refreshToken ->
									postService.getInfo(request, aggregatorDataFetchDetail, body, url, true, refreshToken))
							.thenApplyAsync(partnerResponse -> {
								if (partnerResponse.isPresent()) {
									try {
										if (aggregatorDataFetchDetail.getVendorId() == 1190) {
											return mapper.readValue(partnerResponse.get().toString(), DominosOrderConfirmResponse.class);
										}
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
