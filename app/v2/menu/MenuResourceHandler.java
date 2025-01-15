package v2.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.resources.RequestResource;
import common.response.MenuResponse;
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

public class MenuResourceHandler {
	private final GetService getService;
	private final PostService postService;
	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;
	private final ObjectMapper mapper = new ObjectMapper();

	@Inject
	public MenuResourceHandler(GetService getService, PostService postService, AggregatorDataFetchRepository aggregatorDataFetchRepository) {
		this.getService = getService;
		this.postService = postService;
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
	}

	public CompletionStage<MenuResponse> getPartnerMenuInfo(Long partnerId, Long requestId, RequestResource requestResource, Map<String, String> pathVariables) {
		return aggregatorDataFetchRepository.getData(partnerId).thenComposeAsync(aggregatorDataFetchDetail -> {
			String[] fullOutletURL = aggregatorDataFetchDetail.getMenuUrl().split(" ");
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
									return mapper.readValue(response.get().toString(), MenuResponse.class);
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
									return mapper.readValue(response.get().toString(), MenuResponse.class);
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
