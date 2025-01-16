package v2.cart;

import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static play.mvc.Results.ok;

public class CartController {

	public CompletionStage<Result> getCartInfo() {
		return supplyAsync(() -> ok("test"));
	}

}
