package lib.client;

import lib.client.model.dto.CountryResponse;
import lib.client.model.dto.ResponseWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountryRepository {
    private final Random random = new Random();
    private final List<CountryResponse> countries = new ArrayList<>();

    public void loadCountries(ResponseWrapper responseWrapper) {
        countries.addAll(responseWrapper.data().countries());
    }

    public CountryResponse getRandomCountry() {
        return countries.get(random.nextInt(countries.size()));
    }
}
