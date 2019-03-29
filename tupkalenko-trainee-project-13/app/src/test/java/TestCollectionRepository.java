import com.example.tupkalenko.trainee.project.domain.entity.Collection;
import com.example.tupkalenko.trainee.project.domain.mock.MockCollectionRepository;
import com.example.tupkalenko.trainee.project.domain.repository.CollectionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith (MockitoJUnitRunner.class)
public class TestCollectionRepository {

    private static final int REAL_CITY_ID = 1;
    private static final int REAL_COLLECTION_ID = 1;
    private static final String REAL_IMAGE_URL = "test1";
    private static final int REAL_RESULTS_COUNT = 3;
    private static final String REAL_TITLE = "title1";
    private static final int COLLECTIONS_COUNT = 2;

    private static final int BAD_CITY_ID = 6;

    private CollectionRepository collectionRepository;

    @Before
    public void setUp() {
        collectionRepository = new MockCollectionRepository();
    }

    @Test
    public void testGetCollectionsSuccess() {
        List<Collection> collections = new LinkedList<>();

        Collection expectedCollection = new Collection();
        expectedCollection.setId(REAL_COLLECTION_ID);
        expectedCollection.setTitle(REAL_TITLE);
        expectedCollection.setResultsCount(REAL_RESULTS_COUNT);
        expectedCollection.setImageUrl(REAL_IMAGE_URL);

        collections.add(expectedCollection);
        collectionRepository.getCollectionsByCityId(REAL_CITY_ID)
                            .test()
                            .assertValue(collections1 -> collections1 != null &&
                                    collections1.get(0).equals(expectedCollection));
    }

    @Test
    public void testGetCollectionsError() {
        collectionRepository.getCollectionsByCityId(BAD_CITY_ID)
                            .test()
                            .assertErrorMessage("No collections");
    }

    @Test
    public void testGetCollectionsCountSuccess() {
        List<Collection> collections = new LinkedList<>();

        Collection expectedCollection1 = new Collection();
        expectedCollection1.setId(REAL_COLLECTION_ID);
        expectedCollection1.setTitle(REAL_TITLE);
        expectedCollection1.setResultsCount(REAL_RESULTS_COUNT);
        expectedCollection1.setImageUrl(REAL_IMAGE_URL);

        collections.add(expectedCollection1);
        collections.add(expectedCollection1);

        collectionRepository.getCollectionsByCityId(REAL_CITY_ID, COLLECTIONS_COUNT)
                            .test()
                            .assertValue(result -> result != null &&
                                    collections.size() == COLLECTIONS_COUNT &&
                                    result.get(0).equals(expectedCollection1) &&
                                    result.get(1).equals(expectedCollection1));
    }

    @Test
    public void testGetCollectionsCountError() {
        collectionRepository.getCollectionsByCityId(BAD_CITY_ID, COLLECTIONS_COUNT)
                            .test()
                            .assertErrorMessage("No collections");

    }
}