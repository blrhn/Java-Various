package lib.client.model.dto;

import java.util.List;

public record CountriesResponse(
        List<CountryResponse> countries
) {}
