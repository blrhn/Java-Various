package lib.client;

import lib.client.model.dto.ResponseWrapper;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ApiClient {
    private ApiClient() {}

    private static final String BASE_URI = "https://countries.trevorblades.com";
    private static final String QUERY = """
                query {
                    countries(filter: { code: { in: ["PE", "AR", "VN", "MY", "ML"] } }) {
                        name
                        currency
                        capital
                        languages {
                            name
                        }
                    }
                }""";

    public static ResponseWrapper fetchData() {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(Map.of("query", QUERY));

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URI))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
            }

            return objectMapper.readValue(response.body(), ResponseWrapper.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
