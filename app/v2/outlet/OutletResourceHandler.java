package v2.outlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.resources.RequestResource;
import common.response.StationOutletResponse;
import jakarta.inject.Inject;
import org.apache.commons.collections4.MapUtils;
import play.Logger;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;
import v2.partnerService.GetService;
import v2.partnerService.PostService;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;


public class OutletResourceHandler {

	private final Logger.ALogger logger = Logger.of("v2.outletController");
	private final GetService getService;
	private final PostService postService;
	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;
	private final ObjectMapper mapper = new ObjectMapper();

	@Inject
	public OutletResourceHandler(GetService getService, PostService postService, AggregatorDataFetchRepository aggregatorDataFetchRepository) {
		this.getService = getService;
		this.postService = postService;
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
	}

	public CompletionStage<StationOutletResponse> getPartnerOutlets(Long partnerId, Long requestId, RequestResource requestResource, Map<String, String> pathVariables) {
		return aggregatorDataFetchRepository.getData(partnerId)
				.thenComposeAsync(aggregatorDataFetchDetail -> {
					logger.info("[" + requestId + "] " + " response : " + aggregatorDataFetchDetail);

					String[] fullOutletURL = aggregatorDataFetchDetail.getOutletUrl().split(" ");
					String requestType = fullOutletURL[0];
					AtomicReference<String> url = new AtomicReference<>(fullOutletURL[1]);
					if (!MapUtils.isEmpty(pathVariables)) {
						pathVariables.forEach((name, value) -> url.set(url.get().replace(name, value)));
					}
					if (requestType.equalsIgnoreCase("GET")) {
						return getService.getInfo(requestId, aggregatorDataFetchDetail, requestResource, url)
								.thenApplyAsync(response -> {
									if (response.isPresent()) {
										try {
											return mapper.readValue(response.get().toString(), StationOutletResponse.class);
										} catch (IOException e) {
											return null;
										}
									}
									return null;
								});
					} else if (requestType.equalsIgnoreCase("POST")) {
						return postService.getInfo(requestId, aggregatorDataFetchDetail, requestResource, url)
								.thenApplyAsync(response -> {
									if (response.isPresent()) {
										try {
											return mapper.readValue(response.get().toString(), StationOutletResponse.class);
										} catch (IOException e) {
											return null;
										}
									}
									return null;
								});
					} else {
						return null;
					}
				});
	}
}
