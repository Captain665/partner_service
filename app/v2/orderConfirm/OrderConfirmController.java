package v2.orderConfirm;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import v2.partnerService.PartnerInfo;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class OrderConfirmController {

	private final OrderConfirmResourceHandler resourceHandler;
	private final PartnerInfo partnerInfo;

	private final Logger.ALogger logger = Logger.of("v2.orderConfirmController");

	@Inject
	public OrderConfirmController(OrderConfirmResourceHandler resourceHandler, PartnerInfo partnerInfo) {
		this.resourceHandler = resourceHandler;
		this.partnerInfo = partnerInfo;
	}

	public CompletionStage<Result> getConfirmInfo(Http.Request request, String partnerName) {
		JsonNode json = request.body().asJson();
		logger.info("[" + request.id() + "] " + " partnerName : " + partnerName + " json " + json);

		Long partnerId = partnerInfo.getPartnerInfo(partnerName);
		if (partnerId == null) {
			logger.info("[" + request.id() + "] " + " error : " + "partnerName is required");
			return supplyAsync(() -> badRequest(Json.toJson("partnerName is required")));
		}

		return resourceHandler.orderConfirmByPartner(partnerId, request, json)
				.thenApplyAsync(response -> {
					if (response != null) {
						logger.info("[" + request.id() + "] " + " response : " + response);
						return ok(Json.toJson(response));
					}
					logger.info("[" + request.id() + "] " + " error : " + "oops something went wrong");
					return badRequest(Json.toJson("oops something went wrong"));
				});
	}

}
