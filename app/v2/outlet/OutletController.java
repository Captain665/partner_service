package v2.outlet;

import common.resources.RequestResource;
import jakarta.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
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
		if (requestResource.getPartnerName() == null) {
			return supplyAsync(() -> badRequest(Json.toJson("partnerName should not null")));
		}
		Long partnerId = null;
		if (requestResource.getPartnerName().equalsIgnoreCase("ZOMATO")) {
			partnerId = 49196949L;
		} else if (requestResource.getPartnerName().equalsIgnoreCase("SWIGGY")) {
			partnerId = 49300798L;
		} else {
			return supplyAsync(() -> badRequest(Json.toJson("partnerName is required")));
		}

		Map<String, String> pathVariable = new HashMap<>();
		pathVariable.put("#STATION_CODE#", stationCode);

		return resourceHandler.getPartnerOutlets(partnerId, request.id(), requestResource, pathVariable)
				.thenApplyAsync(
						partnerResponse -> {
							if (partnerResponse != null) {
								return ok(Json.toJson(partnerResponse));
							}
							return badRequest(Json.toJson("oops something went wrong"));
						});

	}

}
