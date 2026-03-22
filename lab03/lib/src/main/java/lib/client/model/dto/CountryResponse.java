package lib.client.model.dto;

import java.util.List;

public record CountryResponse(
    String name,
    String currency,
    String capital,
    List<LanguageResponse> languages
) {}
