import com.example.tupkalenko.trainee.project.domain.entity.City;
import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.CollectionsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.CityRepository;
import com.example.tupkalenko.trainee.project.domain.repository.CollectionRepository;
import com.example.tupkalenko.trainee.project.mvp.contract.CollectionsContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.CollectionsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith (MockitoJUnitRunner.class)
public class CollectionsPresenterTest {

    private static final String EMPTY_CITY_NAME = "";
    private static final String FAKE_CITY_NAME = "testCityName";
    private static final String FAKE_CITY_COUNTRY_NAME = "testCountryName";
    private static final int FAKE_CITY_ID = 1;
    private static final int FAKE_COUNTRY_ID = 1;

    private static final int FAKE_COLLECTION_ID = 2;
    private static final String FAKE_IMAGE_URL = "url";
    private static final int FAKE_RESULTS_COUNT = 1;
    private static final String FAKE_TITLE = "title";

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private CollectionsScreenRouter router;

    @Mock
    private CollectionsContract.CollectionsView view;

    private TestScheduler backgroundScheduler;
    private TestScheduler mainScheduler;
    private CollectionsContract.CollectionsPresenter collectionsPresenter;
    private City fakeCity;
    private Collection fakeCollection;
    private List<Collection> fakeCollections;

    @Before
    public void setup() {
        initFakeCity();
        initFakeCollection();
        initFakeCollectionsList();
        initSchedulers();
        initPresenter();

        doReturn(Single.just(fakeCity)).when(cityRepository)
                .getCity(FAKE_CITY_NAME);

        doReturn(Single.just(fakeCollections)).when(collectionRepository)
                .getCollectionsByCityId(FAKE_CITY_ID);
    }

    private void initSchedulers() {
        backgroundScheduler = new TestScheduler();
        mainScheduler = new TestScheduler();
    }

    private void initPresenter() {
        collectionsPresenter = new CollectionsPresenter(backgroundScheduler,
                                                        mainScheduler,
                                                        router,
                                                        collectionRepository,
                                                        cityRepository);

        collectionsPresenter.attachView(view);
    }

    private void initFakeCollection() {
        fakeCollection = new Collection();

        fakeCollection.setId(FAKE_COLLECTION_ID);
        fakeCollection.setImageUrl(FAKE_IMAGE_URL);
        fakeCollection.setResultsCount(FAKE_RESULTS_COUNT);
        fakeCollection.setTitle(FAKE_TITLE);
    }

    private void initFakeCity() {
        fakeCity = new City();

        fakeCity.setId(FAKE_CITY_ID);
        fakeCity.setName(FAKE_CITY_NAME);
        fakeCity.setCountryName(FAKE_CITY_COUNTRY_NAME);
        fakeCity.setCountryId(FAKE_COUNTRY_ID);
    }

    private void initFakeCollectionsList() {
        fakeCollections = new LinkedList<>();
        fakeCollections.add(new Collection());
        fakeCollections.add(new Collection());
    }

    @Test
    public void testShowRestaurantsScreenSuccess() {
        collectionsPresenter.showRestaurantsScreen(fakeCollection);

        verify(router).showRestaurantsScreen(fakeCollection);
    }

    @Test
    public void testShowRestaurantsScreenError() {
        collectionsPresenter.showRestaurantsScreen(null);

        verify(router, never()).showRestaurantsScreen(any(Collection.class));
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testNavigateBackUnsupported() {
        collectionsPresenter.navigateBack();
    }

    @Test
    public void testSearchSuccess() {
        collectionsPresenter.search(FAKE_CITY_NAME);
        backgroundScheduler.triggerActions();
        mainScheduler.triggerActions();

        InOrder inOrder = Mockito.inOrder(view, cityRepository, collectionRepository);
        inOrder.verify(cityRepository).getCity(FAKE_CITY_NAME);
        inOrder.verify(view).showLoading();
        inOrder.verify(collectionRepository).getCollectionsByCityId(FAKE_CITY_ID);
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).onCollectionsLoaded(fakeCollections);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSearchEmptyQuery() {
        when(cityRepository.getCity(EMPTY_CITY_NAME)).thenReturn(Single.just(fakeCity));

        collectionsPresenter.search(EMPTY_CITY_NAME);
        backgroundScheduler.triggerActions();
        mainScheduler.triggerActions();

        InOrder inOrder = Mockito.inOrder(view, cityRepository, collectionRepository);
        inOrder.verify(cityRepository).getCity(EMPTY_CITY_NAME);
        inOrder.verify(view).showLoading();
        inOrder.verify(collectionRepository).getCollectionsByCityId(anyInt());
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).onCollectionsLoaded(anyList());
        inOrder.verifyNoMoreInteractions();
    }
}