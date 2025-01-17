package v2.outlet;

import common.resources.RequestResource;
import jakarta.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import v2.partnerService.PartnerInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class OutletController {

	private final Logger.ALogger logger = Logger.of("v2.outletController");
	private final OutletResourceHandler resourceHandler;
	private final PartnerInfo partnerInfo;

	@Inject
	public OutletController(OutletResourceHandler resourceHandler, PartnerInfo partnerInfo) {
		this.resourceHandler = resourceHandler;
		this.partnerInfo = partnerInfo;
	}

	public CompletionStage<Result> getStationOutlet(Http.Request request, String stationCode, RequestResource requestResource) {
		logger.info("[" + request.id() + "] " + "requested station " + stationCode + " filters : " + requestResource);
		requestResource.setStationCode(stationCode);

		Long partnerId = partnerInfo.getPartnerInfo(requestResource.getPartnerName() == null ? "" : requestResource.getPartnerName());
		if (partnerId == null) {
			logger.info("[" + request.id() + "] " + " error : " + "partnerName is required");
			return supplyAsync(() -> badRequest(Json.toJson("partnerName is required")));
		}

		Map<String, String> pathVariable = new HashMap<>();
		pathVariable.put("#STATION_CODE#", stationCode);

		return resourceHandler.getPartnerOutlets(partnerId, request, requestResource, pathVariable)
				.thenApplyAsync(
						partnerResponse -> {
							if (partnerResponse != null) {
								return ok(Json.toJson(partnerResponse));
							}
							return badRequest(Json.toJson("oops, something went wrong"));
						});

	}

}
