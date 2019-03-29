import com.example.tupkalenko.trainee.project.domain.entity.Location;
import com.example.tupkalenko.trainee.project.domain.entity.Restaurant;
import com.example.tupkalenko.trainee.project.domain.entity.UserRating;
import com.example.tupkalenko.trainee.project.domain.navigation.contract.RestaurantDetailsScreenRouter;
import com.example.tupkalenko.trainee.project.domain.repository.RestaurantRepository;
import com.example.tupkalenko.trainee.project.mvp.contract.RestaurantDetailsContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.RestaurantDetailsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith (MockitoJUnitRunner.class)
public class RestaurantDetailsPresenterTest {

    private final static int FAKE_RESTAURANT_ID = 1;
    private final static int FAKE_RESTAURANT_BAD_ID = -1;
    private final static String FAKE_RESTAURANT_NAME = "test";
    private static final float FAKE_AVERAGE_COST_FOR_TWO = 34.5f;
    private static final String FAKE_CURRENCY = "$";
    private static final String FAKE_FEATURED_IMAGE = "https://testhost/testpath";
    private Restaurant fakeRestaurant;
    private Restaurant fakeBadRestaurant;
    private TestScheduler backgroundScheduler;
    private TestScheduler mainScheduler;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantDetailsContract.RestaurantsDetailsView view;

    @Mock
    private RestaurantDetailsScreenRouter router;

    private RestaurantDetailsPresenter restaurantDetailsPresenter;

    @Before
    public void setup() {
        initFakeRestaurant();
        initFakeBadRestaurant();
        initSchedulers();
        initPresenter();

        doReturn(Single.just(fakeRestaurant))
                .when(restaurantRepository)
                .getRestaurant(FAKE_RESTAURANT_ID);

        doReturn(Single.error(new Throwable()))
                .when(restaurantRepository)
                .getRestaurant(FAKE_RESTAURANT_BAD_ID);
    }

    private void initFakeRestaurant() {
        fakeRestaurant = new Restaurant();
        fakeRestaurant.setId(FAKE_RESTAURANT_ID);
        fakeRestaurant.setName(FAKE_RESTAURANT_NAME);
        fakeRestaurant.setAverageCostForTwo(FAKE_AVERAGE_COST_FOR_TWO);
        fakeRestaurant.setCurrency(FAKE_CURRENCY);
        fakeRestaurant.setFeaturedImage(FAKE_FEATURED_IMAGE);
        fakeRestaurant.setLocation(mock(Location.class));
        fakeRestaurant.setUserRating(mock(UserRating.class));
    }

    private void initFakeBadRestaurant() {
        fakeBadRestaurant = new Restaurant();
        fakeBadRestaurant.setId(FAKE_RESTAURANT_BAD_ID);
    }

    private void initSchedulers() {
        backgroundScheduler = new TestScheduler();
        mainScheduler = new TestScheduler();
    }

    private void initPresenter() {
        restaurantDetailsPresenter = new RestaurantDetailsPresenter(backgroundScheduler,
                                                                    mainScheduler,
                                                                    router,
                                                                    restaurantRepository);
        restaurantDetailsPresenter.attachView(view);
    }

    @Test
    public void testSearchSuccess() {
        restaurantDetailsPresenter.loadDetails(fakeRestaurant);
        backgroundScheduler.triggerActions();
        mainScheduler.triggerActions();

        InOrder inOrder = Mockito.inOrder(view, restaurantRepository);
        inOrder.verify(restaurantRepository).getRestaurant(FAKE_RESTAURANT_ID);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).onRestaurantLoaded(fakeRestaurant);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testSearchError() {
        restaurantDetailsPresenter.loadDetails(fakeBadRestaurant);
        backgroundScheduler.triggerActions();
        mainScheduler.triggerActions();

        InOrder inOrder = Mockito.inOrder(view, restaurantRepository);
        inOrder.verify(restaurantRepository).getRestaurant(FAKE_RESTAURANT_BAD_ID);
        inOrder.verify(view).showLoading();
        inOrder.verify(view).hideLoading();
        inOrder.verify(view).showUnexpectedError(any(Throwable.class));
        inOrder.verifyNoMoreInteractions();
    }
}