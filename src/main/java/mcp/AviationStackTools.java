package mcp;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import rest.AviationStackClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
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

    @McpTool(description = "Get real-time flight information. Returns flight details including status, aircraft type, and airline information.")
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

    @McpTool(description = "Get airline information including IATA/ICAO codes and names.")
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

    @McpTool(description = "Get airport information including location, IATA/ICAO codes and names.")
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

    @McpTool(description = "Get flight routes between airports. All filters are optional.")
    public Object getRoutes(
            @McpToolParam(description = "Departure airport IATA code (e.g., FRA, SFO)", required = false) String depIata,
            @McpToolParam(description = "Departure airport ICAO code (e.g., EDDF, KSFO)", required = false) String depIcao,
            @McpToolParam(description = "Arrival airport IATA code (e.g., JFK, LHR)", required = false) String arrIata,
            @McpToolParam(description = "Arrival airport ICAO code (e.g., KJFK, EGLL)", required = false) String arrIcao,
            @McpToolParam(description = "Airline IATA code (e.g., LH, AA)", required = false) String airlineIata,
            @McpToolParam(description = "Airline ICAO code (e.g., DLH, AAL)", required = false) String airlineIcao,
            @McpToolParam(description = "Flight number (e.g., 400)", required = false) String flightNum,
            @McpToolParam(description = "Pagination offset (default: 0)", required = false) Integer offset,
            @McpToolParam(description = "Limit number of results (default: 10)", required = false) Integer limit
    ) throws Exception {
        Map<String, String> params = new HashMap<>();

        if (depIata != null) {
            params.put("dep_iata", depIata);
        }
        if (depIcao != null) {
            params.put("dep_icao", depIcao);
        }
        if (arrIata != null) {
            params.put("arr_iata", arrIata);
        }
        if (arrIcao != null) {
            params.put("arr_icao", arrIcao);
        }
        if (airlineIata != null) {
            params.put("airline_iata", airlineIata);
        }
        if (airlineIcao != null) {
            params.put("airline_icao", airlineIcao);
        }
        if (flightNum != null) {
            params.put("flight_num", flightNum);
        }
        if (offset != null) {
            params.put("offset", offset.toString());
        }
        if (limit != null) {
            params.put("limit", limit.toString());
        } else {
            params.put("limit", "10");
        }

        String response = client.getRoutes(params);
        return parseResponse(response);
    }

    @McpTool(description = "Get scheduled flights for an airport. Without a date (or within 7 days) returns today's live timetable. A date more than 7 days ahead uses the future flights endpoint for that specific date. Rate limit: 1 request per 10 seconds (paid) or 1 per 60 seconds (free). Only high-traffic airports are supported.")
    public Object getScheduledFlights(
            @McpToolParam(description = "Airport IATA code (e.g., FRA, JFK, LHR)", required = true) String iataCode,
            @McpToolParam(description = "Flight direction: 'arrival' or 'departure'", required = true) String type,
            @McpToolParam(description = "Date in YYYY-MM-DD format. Only applies for dates more than 7 days in the future.", required = false) String date,
            @McpToolParam(description = "Airline IATA code to filter results (e.g., LH, BA). Timetable only.", required = false) String airlineIata,
            @McpToolParam(description = "Airline ICAO code to filter results (e.g., DLH). Timetable only.", required = false) String airlineIcao,
            @McpToolParam(description = "Airline name to filter results (e.g., Lufthansa). Timetable only.", required = false) String airlineName,
            @McpToolParam(description = "Flight IATA number to filter results (e.g., LH400). Timetable only.", required = false) String flightIata,
            @McpToolParam(description = "Flight ICAO number to filter results (e.g., DLH400). Timetable only.", required = false) String flightIcao,
            @McpToolParam(description = "Flight number to filter results (e.g., 400). Timetable only.", required = false) String flightNum,
            @McpToolParam(description = "Flight status: landed, scheduled, cancelled, active, incident, diverted, redirected, unknown. Timetable only.", required = false) String status,
            @McpToolParam(description = "Departure terminal (e.g., 1, A). Timetable only.", required = false) String depTerminal,
            @McpToolParam(description = "Arrival terminal (e.g., 2, B). Timetable only.", required = false) String arrTerminal,
            @McpToolParam(description = "Pagination offset (default: 0). Timetable only.", required = false) Integer offset,
            @McpToolParam(description = "Limit number of results (default: 10)", required = false) Integer limit
    ) throws Exception {
        Map<String, String> params = new HashMap<>();

        params.put("iataCode", iataCode);
        params.put("type", type);
        if (limit != null) {
            params.put("limit", limit.toString());
        } else {
            params.put("limit", "10");
        }

        boolean useFuture = date != null && LocalDate.parse(date).isAfter(LocalDate.now().plusDays(7));

        if (useFuture) {
            params.put("date", date);
            String response = client.getFutureFlights(params);
            return parseResponse(response);
        }

        if (airlineIata != null) {
            params.put("airline_iata", airlineIata);
        }
        if (airlineIcao != null) {
            params.put("airline_icao", airlineIcao);
        }
        if (airlineName != null) {
            params.put("airline_name", airlineName);
        }
        if (flightIata != null) {
            params.put("flight_iata", flightIata);
        }
        if (flightIcao != null) {
            params.put("flight_icao", flightIcao);
        }
        if (flightNum != null) {
            params.put("flight_num", flightNum);
        }
        if (status != null) {
            params.put("status", status);
        }
        if (depTerminal != null) {
            params.put("dep_terminal", depTerminal);
        }
        if (arrTerminal != null) {
            params.put("arr_terminal", arrTerminal);
        }
        if (offset != null) {
            params.put("offset", offset.toString());
        }

        String response = client.getTimetable(params);
        return parseResponse(response);
    }

    private Object parseResponse(String response) {
        try {
            return objectMapper.readValue(response, Object.class);
        } catch (Exception e) {
            return response;
        }
    }
}
