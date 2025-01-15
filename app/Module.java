import com.google.inject.AbstractModule;
import v2.aggregatorDataFetch.AggregatorDataFetchRepository;
import v2.aggregatorDataFetch.JPAAggregatorDataFetchRepository;

public class Module extends AbstractModule {

	@Override
	protected void configure() {
		bind(AggregatorDataFetchRepository.class).to(JPAAggregatorDataFetchRepository.class);
	}


}
