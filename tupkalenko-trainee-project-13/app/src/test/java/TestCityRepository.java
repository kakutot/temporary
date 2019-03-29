import com.example.tupkalenko.trainee.project.domain.entity.City;
import com.example.tupkalenko.trainee.project.domain.mock.MockCityRepository;
import com.example.tupkalenko.trainee.project.domain.repository.CityRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith (MockitoJUnitRunner.class)
public class TestCityRepository {

    private static final int REAL_CITY_ID = 1;
    private static final String REAL_CITY_NAME = "test1";
    private static final String REAL_COUNTRY_NAME = "tesCountryName1";
    private static final int REAL_COUNTRY_ID = 1;

    private static final String BAD_CITY_NAME = "test6";
    private CityRepository cityRepository;

    @Before
    public void setUp() {
        cityRepository = new MockCityRepository();
    }

    @Test
    public void testGetCitySuccess() {
        City expectedCity = new City();
        expectedCity.setId(REAL_CITY_ID);
        expectedCity.setName(REAL_CITY_NAME);
        expectedCity.setCountryName(REAL_COUNTRY_NAME);
        expectedCity.setCountryId(REAL_COUNTRY_ID);

        cityRepository.getCity(REAL_CITY_NAME)
                      .test()
                      .assertValue(expectedCity)
                      .assertNoErrors();
    }

    @Test
    public void testGetCityError() {
        cityRepository.getCity(BAD_CITY_NAME)
                      .test()
                      .assertErrorMessage("No such city");
    }
}