package mcp;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import rest.AviationStackClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Component
public class AviationStackTools {

    private final AviationStackClient client;
    private final ObjectMapper objectMapper;

    public AviationStackTools(AviationStackClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    @McpTool(description = "Get flight information from AviationStack API. Returns flight details including status, aircraft type, and airline information.")
    public Object getFlights(
            @McpToolParam(description = "Airline IATA code (e.g., LH, AA, UA)", required = false) String airlineIata,
            @McpToolParam(description = "Limit number of results (default: 10)", required = false) Integer limit
    ) throws Exception {
        Map<String, String> params = new HashMap<>();

        if (airlineIata != null) {
            params.put("airline_iata", airlineIata);
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        } else {
            params.put("limit", "10");
        }

        String response = client.getFlights(params);
        return parseResponse(response);
    }

    @McpTool(description = "Get airline information from AviationStack API. Returns details about airlines including their IATA/ICAO codes and names.")
    public Object getAirlines(
            @McpToolParam(description = "Limit number of results (default: 10)", required = false) Integer limit
    ) throws Exception {
        Map<String, String> params = new HashMap<>();

        if (limit != null) {
            params.put("limit", limit.toString());
        } else {
            params.put("limit", "10");
        }

        String response = client.getAirlines(params);
        return parseResponse(response);
    }

    @McpTool(description = "Get airport information from AviationStack API. Returns airport details including location, IATA codes, and airport names.")
    public Object getAirports(
            @McpToolParam(description = "Country name (e.g., Germany, United States)", required = false) String countryName,
            @McpToolParam(description = "Limit number of results (default: 10)", required = false) Integer limit
    ) throws Exception {
        Map<String, String> params = new HashMap<>();

        if (countryName != null) {
            params.put("country_name", countryName);
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        } else {
            params.put("limit", "10");
        }

        String response = client.getAirports(params);
        return parseResponse(response);
    }

    /**
     * Parse JSON response string into a structured object
     */
    private Object parseResponse(String response) {
        try {
            return objectMapper.readValue(response, Object.class);
        } catch (Exception e) {
            // If parsing fails, return the raw string
            return response;
        }
    }
}