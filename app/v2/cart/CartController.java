package v2.cart;

import com.fasterxml.jackson.databind.JsonNode;
import common.resources.ApiResponse;
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

public class CartController {
	private final Logger.ALogger logger = Logger.of("v2.cartController");
	private final CartResourceHandler resourceHandler;
	private final PartnerInfo partnerInfo;

	@Inject
	public CartController(CartResourceHandler resourceHandler, PartnerInfo partnerInfo) {
		this.resourceHandler = resourceHandler;
		this.partnerInfo = partnerInfo;
	}

	public CompletionStage<Result> getCartInfo(Http.Request request, String partnerName) {
		JsonNode json = request.body().asJson();
		logger.info("[" + request.id() + "] " + " partnerName : " + partnerName + " json : " + json);

		Long partnerId = partnerInfo.getPartnerInfo(partnerName);
		if (partnerId == null) {
			logger.info("[" + request.id() + "] " + " error : " + "partnerName is required");
			return supplyAsync(() -> badRequest(Json.toJson("partnerName is required")));
		}

		Map<String, String> pathVariable = new HashMap<>();
		pathVariable.put("#CART_ID#", request.queryString("cart_id").isPresent() ?
				request.queryString("cart_id").get() : "");

		return resourceHandler.cartValidateByPartner(partnerId, request, json, pathVariable)
				.thenApplyAsync(
						response -> {
							if (response != null) {
								logger.info("[" + request.id() + "] " + " response : " + response);
								return ok(Json.toJson(response));
							}
							logger.info("[" + request.id() + "] " + " error : " + "oops something went wrong");
							return badRequest(Json.toJson(new ApiResponse("oops something went wrong")));
						});


	}
}
