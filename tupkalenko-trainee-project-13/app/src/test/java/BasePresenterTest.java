import com.example.tupkalenko.trainee.project.domain.navigation.contract.Router;
import com.example.tupkalenko.trainee.project.mvp.BaseContract;
import com.example.tupkalenko.trainee.project.mvp.BasePresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith (RobolectricTestRunner.class)
public class BasePresenterTest {

    private BasePresenter basePresenter;
    private BaseContract.BaseView mockView;
    private TestScheduler backgroundScheduler;
    private TestScheduler mainScheduler;
    private Router router;

    @Before
    public void setUp() {
        initSchedulers();
        initRouter();
        initPresenter();
        initView();
    }

    private void initPresenter() {
        basePresenter = new BasePresenter(backgroundScheduler, mainScheduler, router) {};
    }

    private void initRouter() {
        router = mock(Router.class);
    }

    private void initSchedulers() {
        backgroundScheduler = new TestScheduler();
        mainScheduler = new TestScheduler();
    }

    private void initView() {
        mockView = mock(BaseContract.BaseView.class);
    }

    @Test
    public void testViewIsCorrectlyAttached() {
        basePresenter.attachView(mockView);

        Assert.assertTrue(basePresenter.isViewAttached());
        assertEquals(mockView, basePresenter.getView());
    }

    @Test ()
    public void testGetViewReturnsView() {
        basePresenter.attachView(mockView);

        Assert.assertNotNull(basePresenter.getView());
    }

    @Test (expected = NullPointerException.class)
    public void testGetViewForNullableViewThrowsNPE() {
        basePresenter.attachView(null);
        basePresenter.getView();
    }

    @Test (expected = NullPointerException.class)
    public void testViewIsDetached() {
        basePresenter.detachView();
        basePresenter.getView();
    }

    @Test
    public void testViewShowsUnexpectedError() {
        Throwable throwable = Mockito.mock(Throwable.class);
        basePresenter.attachView(mockView);
        basePresenter.onError(throwable);

        verify(basePresenter.getView()).showUnexpectedError(throwable);
    }
}