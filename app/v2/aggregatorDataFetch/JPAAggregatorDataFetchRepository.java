package v2.aggregatorDataFetch;

import common.model.AggregatorDataFetchDetail;
import jakarta.inject.Inject;
import jakarta.persistence.TypedQuery;
import play.db.jpa.JPAApi;

import jakarta.persistence.EntityManager;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAAggregatorDataFetchRepository implements AggregatorDataFetchRepository {

	private final JPAApi jpaApi;

	@Inject
	public JPAAggregatorDataFetchRepository(JPAApi jpaApi) {
		this.jpaApi = jpaApi;
	}

	@Override
	public CompletionStage<AggregatorDataFetchDetail> getData(Long vendorId) {
		return supplyAsync(() -> wrap(em -> {
			TypedQuery<AggregatorDataFetchDetail> query = em.createQuery("Select m from AggregatorDataFetchDetail m where vendorId = :vendorId", AggregatorDataFetchDetail.class)
					.setParameter("vendorId", vendorId);
			return query.setMaxResults(1).getSingleResult();
		}));
	}

	private <T> T wrap(Function<EntityManager, T> function) {
		return jpaApi.withTransaction(function);
	}
}
