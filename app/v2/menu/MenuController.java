package v2.menu;

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

public class MenuController {

	private final MenuResourceHandler resourceHandler;
	private final PartnerInfo partnerInfo;
	private final Logger.ALogger logger = Logger.of("v2.outletController");

	@Inject
	public MenuController(MenuResourceHandler resourceHandler, PartnerInfo partnerInfo) {
		this.resourceHandler = resourceHandler;
		this.partnerInfo = partnerInfo;
	}

	public CompletionStage<Result> getPartnerMenuInfo(Http.Request request, String outletId, RequestResource requestResource) {
		logger.info("[" + request.id() + "] " + "requested outlet_id " + outletId + " requestResource : " + requestResource.toString());
		requestResource.setOutletId(outletId);

		Long partnerId = partnerInfo.getPartnerInfo(requestResource.getPartnerName() == null ? "" : requestResource.getPartnerName());
		if (partnerId == null) {
			logger.info("[" + request.id() + "] " + " error : " + "partnerName is required");
			return supplyAsync(() -> badRequest(Json.toJson("partnerName is required")));
		}

		Map<String, String> pathVariable = new HashMap<>();
		pathVariable.put("#OUTLET_ID#", outletId);

		return resourceHandler.getPartnerMenuInfo(partnerId, request.id(), requestResource, pathVariable)
				.thenApplyAsync(response -> {
					if (response != null) {
						return ok(Json.toJson(response));
					}
					return badRequest(Json.toJson("oops something went wrong"));
				});
	}
}
