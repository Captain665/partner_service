package v2.orderConfirm;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static play.mvc.Results.badRequest;

public class OrderConfirmController {

	private final Logger.ALogger logger = Logger.of("v2.orderConfirmController");

	public CompletionStage<Result> getConfirmInfo(Http.Request request, String partnerName) {
		JsonNode json = request.body().asJson();
		logger.info("[" + request.id() + "] " + " partnerName : " + partnerName + " json " + json);
		return supplyAsync(() -> badRequest(Json.toJson("partnerName is required")));
	}

}
