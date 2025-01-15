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
	private final OutletResourceHandler resourceHandler;

	@Inject
	public OutletController(OutletResourceHandler resourceHandler) {
		this.resourceHandler = resourceHandler;
	}

	public CompletionStage<Result> getStationOutlet(Http.Request request, String stationCode, RequestResource requestResource) {
		logger.info("[" + request.id() + "] " + "requested station " + stationCode + " filters : " + requestResource);
		requestResource.setStationCode(stationCode);
		Long partnerId = 0L;
		if (requestResource.getPartnerName().equalsIgnoreCase("ZOMATO")) {
			partnerId = 49196949L;
		} else if (requestResource.getPartnerName().equalsIgnoreCase("SWIGGY")) {
			partnerId = 49300798L;
		}
		Map<String, String> pathVariable = new HashMap<>();
		pathVariable.put("#STATION_CODE#", stationCode);

		return resourceHandler.getPartnerOutlets(partnerId, request.id(), requestResource, stationCode, pathVariable)
				.thenApplyAsync(
						partnerResponse -> {
							if (partnerResponse != null) {
								return ok(Json.toJson(partnerResponse));
							}
							return badRequest(Json.toJson("bad error "));
						});

	}

}
