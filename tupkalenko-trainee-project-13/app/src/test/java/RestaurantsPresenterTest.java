import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.entity.Location;
import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.domain.entity.UserRating;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;
import com.example.tupkalenko.trainee.project.mvp.contract.RestaurantsContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.RestaurantsPresenter;

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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith (MockitoJUnitRunner.class)
public class RestaurantsPresenterTest {

    private static final int FAKE_COLLECTION_ID = 2;
    private static final String FAKE_IMAGE_URL = "url";
    private static final int FAKE_RESULTS_COUNT = 1;
    private static final String FAKE_TITLE = "title";
    private static final int FAKE_START = 0;
    private static final int FAKE_BAD_START = -1;
    private static final int FAKE_COUNT = 2;
    private static final int FAKE_BAD_COUNT = -1;

    private static final int FAKE_RESTAURANT_ID = 1;
    private static final String FAKE_RESTAURANT_NAME = "test";
    private static final float FAKE_AVERAGE_COST_FOR_TWO = 34.5f;
    private static final String FAKE_CURRENCY = "$";
    private static final String FAKE_FEATURED_IMAGE = "https://testhost/testpath";

    private static final int FAKE_RESTAURANT_2_ID = 1;
    private static final String FAKE_RESTAURANT_2_NAME = "test";
    private static final float FAKE_AVERAGE_COST_FOR_TWO_2 = 34.5f;
    private static final String FAKE_CURRENCY_2 = "€";
    private static final String FAKE_FEATURED_IMAGE_2 = "https://testhost/testpath2";

    private static final String FAKE_EMPTY_RESTAURANT_NAME = "";

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantsScreenRouter router;

    @Mock
    private RestaurantsContract.RestaurantsView view;

    private TestScheduler backgroundScheduler;
    private TestScheduler mainScheduler;

    private RestaurantsPresenter restaurantsPresenter;
    private Collection fakeCollection;
    private List<Restaurant> fakeRestaurants;
    private Restaurant fakeRestaurant;
    private Restaurant fakeRestaurant2;

    @Before
    public void setup() {
        initFakeRestaurants();
        initFakeCollection();
        initSchedulers();
        initPresenter();

        doReturn(Single.just(fakeRestaurants)).when(restaurantRepository)
                .getRestaurantsByCollectionId(FAKE_RESTAURANT_NAME,
                                              FAKE_COLLECTION_ID,
                                              FAKE_START,
                                              FAKE_COUNT);
    }

    private void initFakeCollection() {
        fakeCollection = new Collection();

        fakeCollection.setId(FAKE_COLLECTION_ID);
        fakeCollection.setImageUrl(FAKE_IMAGE_URL);
        fakeCollection.setResultsCount(FAKE_RESULTS_COUNT);
        fakeCollection.setTitle(FAKE_TITLE);
    }

    private void initFakeRestaurants() {
        fakeRestaurants = new LinkedList<>();
        initFakeRestaurant1();
        initFakeRestaurant2();

        fakeRestaurants.add(fakeRestaurant);
        fakeRestaurants.add(fakeRestaurant2);
    }

    private void initFakeRestaurant1() {
        fakeRestaurant = new Restaurant();
        fakeRestaurant.setId(FAKE_RESTAURANT_ID);
        fakeRestaurant.setName(FAKE_RESTAURANT_NAME);
        fakeRestaurant.setAverageCostForTwo(FAKE_AVERAGE_COST_FOR_TWO);
        fakeRestaurant.setCurrency(FAKE_CURRENCY);
        fakeRestaurant.setFeaturedImage(FAKE_FEATURED_IMAGE);
        fakeRestaurant.setLocation(mock(Location.class));
        fakeRestaurant.setUserRating(mock(UserRating.class));
    }

    private void initFakeRestaurant2() {
        fakeRestaurant2 = new Restaurant();
        fakeRestaurant2.setId(FAKE_RESTAURANT_2_ID);
        fakeRestaurant2.setName(FAKE_RESTAURANT_2_NAME);
        fakeRestaurant2.setAverageCostForTwo(FAKE_AVERAGE_COST_FOR_TWO_2);
        fakeRestaurant.setCurrency(FAKE_CURRENCY_2);
        fakeRestaurant.setFeaturedImage(FAKE_FEATURED_IMAGE_2);
        fakeRestaurant.setLocation(mock(Location.class));
        fakeRestaurant.setUserRating(mock(UserRating.class));

    }

    private void initSchedulers() {
        backgroundScheduler = new TestScheduler();
        mainScheduler = new TestScheduler();
    }

    private void initPresenter() {
        restaurantsPresenter = new RestaurantsPresenter(backgroundScheduler,
                                                        mainScheduler,
                                                        router,
                                                        restaurantRepository);
        restaurantsPresenter.attachView(view);
    }

    @Test
    public void testShowRestaurantDetailsSuccess() {
        restaurantsPresenter.showRestaurantDetails(fakeRestaurant);

        verify(router).showRestaurantDetails(fakeRestaurant);
    }

    @Test
    public void testShowRestaurantsScreenError() {
        restaurantsPresenter.showRestaurantDetails(null);

        verify(router, never()).showRestaurantDetails(any(Restaurant.class));
    }

    @Test
    public void testNavigateBackSuccess() {
        restaurantsPresenter.navigateBack();

        verify(router).navigateBack();
    }

    @Test
    public void testSearchSuccess() {
        restaurantsPresenter.search(FAKE_RESTAURANT_NAME, fakeCollection, FAKE_START, FAKE_COUNT);
        backgroundScheduler.triggerActions();
        mainScheduler.triggerActions();

        InOrder inOrder = Mockito.inOrder(view, restaurantRepository);
        inOrder.verify(restaurantRepository).getRestaurantsByCollectionId(FAKE_RESTAURANT_NAME,
                                                                          FAKE_COLLECTION_ID,
                                                                          FAKE_START,
                                                                          FAKE_COUNT);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).onRestaurantsListLoaded(fakeRestaurants);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSearchEmptyQuery() {
        when(restaurantRepository.getRestaurantsByCollectionId(FAKE_EMPTY_RESTAURANT_NAME,
                                                               FAKE_COLLECTION_ID,
                                                               FAKE_START,
                                                               FAKE_COUNT))
                .thenReturn(Single.just(fakeRestaurants));

        restaurantsPresenter.search(FAKE_EMPTY_RESTAURANT_NAME, fakeCollection, FAKE_START, FAKE_COUNT);
        backgroundScheduler.triggerActions();
        mainScheduler.triggerActions();

        InOrder inOrder = Mockito.inOrder(view, restaurantRepository);
        inOrder.verify(restaurantRepository).getRestaurantsByCollectionId(FAKE_EMPTY_RESTAURANT_NAME,
                                                                          FAKE_COLLECTION_ID,
                                                                          FAKE_START,
                                                                          FAKE_COUNT);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).onRestaurantsListLoaded(anyListOf(Restaurant.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSearchError() {
        restaurantsPresenter.search(FAKE_EMPTY_RESTAURANT_NAME,
                                    fakeCollection,
                                    FAKE_BAD_START,
                                    FAKE_BAD_COUNT);
        backgroundScheduler.triggerActions();
        mainScheduler.triggerActions();

        InOrder inOrder = Mockito.inOrder(view, restaurantRepository);
        inOrder.verify(restaurantRepository, never()).getRestaurantsByCollectionId(anyString(),
                                                                                   anyInt(),
                                                                                   anyInt(),
                                                                                   anyInt());
        inOrder.verify(view, never()).showLoading();
        inOrder.verify(view, never()).hideLoading();
        inOrder.verify(view, never()).onRestaurantsListLoaded(anyListOf(Restaurant.class));
        inOrder.verifyNoMoreInteractions();
    }
}