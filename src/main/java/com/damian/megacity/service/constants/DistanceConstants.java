package com.damian.megacity.service.constants;

public class DistanceConstants {
    public final static String DISTANCE_CONTROLLER = "distanceController";
    public final static String DISTANCE_ENDPOINT = "/api/v1/distance";

    public final static String GOOGLE_API_KEY_ENV = "GOOGLE_API_KEY";
    public final static String API_URL_TEMPLATE = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s";

    public final static String ORIGINS_PARAM = "origins";
    public final static String DESTINATIONS_PARAM = "destinations";

    public final static String API_KEY_MISSING_MSG = "API key is missing.";
    public final static String API_CALL_ERROR_MSG = "An error occurred while calling Google Maps API: %s";

    private DistanceConstants() {
    }
}
