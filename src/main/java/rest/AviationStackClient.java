package rest;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

//https://docs.apilayer.com/aviationstack/docs/aviationstack-api-v-1-0-0

public class AviationStackClient {

    private static final String BASE_URL = "http://api.aviationstack.com/v1";
    private final String apiKey;
    private final HttpClient httpClient;

    public AviationStackClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String getFlights(Map<String, String> params)
            throws IOException, InterruptedException {

        return sendGetRequest("/flights", params);
    }

    public String getAirlines(Map<String, String> params)
            throws IOException, InterruptedException {

        return sendGetRequest("/airlines", params);
    }

    public String getAirports(Map<String, String> params)
            throws IOException, InterruptedException {

        return sendGetRequest("/airports", params);
    }

    public String getRoutes(Map<String, String> params)
            throws IOException, InterruptedException {

        return sendGetRequest("/routes", params);
    }

    public String getFutureFlights(Map<String, String> params)
            throws IOException, InterruptedException {

        return sendGetRequest("/flightsFuture", params);
    }

    public String getTimetable(Map<String, String> params)
            throws IOException, InterruptedException {

        return sendGetRequest("/timetable", params);
    }

    private String sendGetRequest(String endpoint, Map<String, String> params)
            throws IOException, InterruptedException {

        String query = params.entrySet()
                .stream()
                .map(e -> encode(e.getKey()) + "=" + encode(e.getValue()))
                .collect(Collectors.joining("&"));

        String url = BASE_URL + endpoint
                + "?access_key=" + encode(apiKey)
                + (query.isEmpty() ? "" : "&" + query);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode() + " - " + response.body());
        }

        return response.body();
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
