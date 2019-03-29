import com.example.tupkalenko.trainee.project.domain.navigation.contract.MainRouter;
import com.example.tupkalenko.trainee.project.mvp.contract.MainContract;
import com.example.tupkalenko.trainee.project.mvp.presenter.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

@RunWith (MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainContract.MainView mainView;

    @Mock
    MainRouter router;

    private TestScheduler backgroundScheduler;
    private TestScheduler mainScheduler;
    private MainContract.MainPresenter presenter;

    @Before
    public void setup() {
        initSchedulers();
        initPresenter();
    }

    private void initSchedulers() {
        backgroundScheduler = new TestScheduler();
        mainScheduler = new TestScheduler();
    }

    private void initPresenter() {
        presenter = new MainPresenter(backgroundScheduler, mainScheduler, router);
        presenter.attachView(mainView);
    }

    @Test
    public void testShowCollectionsScreenSuccess() {
        presenter.showCollectionsScreen();

        verify(router).showCollectionsScreen();
    }
}