package v2.menu;

import play.mvc.Result;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static play.mvc.Results.ok;

public class MenuController {

	public CompletionStage<Result> getMenuInfo() {
		return supplyAsync(() -> ok("test"));
	}
}
