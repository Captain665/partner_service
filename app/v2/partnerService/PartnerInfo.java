package v2.partnerService;

import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class PartnerInfo {

	public Long getPartnerInfo(String partnerName) {
		if (partnerName.equalsIgnoreCase("ZOMATO")) {
			return 49196949L;
		} else if (partnerName.equalsIgnoreCase("SWIGGY")) {
			return 49300798L;
		} else if (partnerName.equalsIgnoreCase("DOMINOS")) {
			return 1190L;
		} else {
			return null;
		}
	}
}
