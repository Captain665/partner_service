package v2.outlet;

import common.resources.RequestResource;
import jakarta.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class OutletController {

	private final Logger.ALogger logger = Logger.of("v2.outletController");
	private final PartnerOutletService partnerOutletService;
	private final AggregatorDataFetchRepository aggregatorDataFetchRepository;

	@Inject
	public OutletController(PartnerOutletService partnerOutletService, AggregatorDataFetchRepository aggregatorDataFetchRepository) {
		this.partnerOutletService = partnerOutletService;
		this.aggregatorDataFetchRepository = aggregatorDataFetchRepository;
	}

	public CompletionStage<Result> getStationOutlet(Http.Request request, String stationCode, RequestResource requestResource) {
		logger.info("request station code " + stationCode + " requestResource : " + requestResource);
		requestResource.setStationCode(stationCode);
		Long partnerId = 0L;
		if (requestResource.getPartnerName().equalsIgnoreCase("ZOMATO")) {
			partnerId = 49196949L;
		} else if (requestResource.getPartnerName().equalsIgnoreCase("SWIGGY")) {
			partnerId = 49300798L;
		}

		return aggregatorDataFetchRepository.getData(partnerId)
				.thenComposeAsync(aggregatorDataFetchDetail -> {
					logger.info("aggregatorDataFetchDetail : ", aggregatorDataFetchDetail.toString());
					Map<String, String> pathVariable = new HashMap<>();
					pathVariable.put("#STATION_CODE#", stationCode);
					return partnerOutletService.partnerStationOutlet(request.id(), aggregatorDataFetchDetail, requestResource, pathVariable)
							.thenApplyAsync(partnerOutletService -> {
								if (partnerOutletService.isPresent()) {
									logger.info("[" + request.id() + "] " + " response : " + partnerOutletService.get());
									return ok(Json.toJson(partnerOutletService.get()));
								}
								return badRequest(Json.toJson("Oops something went wrong"));
							});
				});
	}

}
