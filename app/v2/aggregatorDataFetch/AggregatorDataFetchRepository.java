package v2.aggregatorDataFetch;

import common.model.AggregatorDataFetchDetail;

import java.util.concurrent.CompletionStage;

public interface AggregatorDataFetchRepository {

	CompletionStage<AggregatorDataFetchDetail> getData(Long vendorId);
}
