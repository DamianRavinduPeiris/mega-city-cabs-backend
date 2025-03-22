package com.damian.megacity.service.constants;

public class DistanceConstants {
    public static final String DISTANCE_CONTROLLER = "distanceController";
    public static final String DISTANCE_ENDPOINT = "/api/v1/distance";

    public static final String GOOGLE_API_KEY_ENV = "GOOGLE_API_KEY";
    public static final String API_URL_TEMPLATE = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s";

    public static final String ORIGINS_PARAM = "origins";
    public static final String DESTINATIONS_PARAM = "destinations";

    public static final String API_KEY_MISSING_MSG = "API key is missing.";
    public static final String API_CALL_ERROR_MSG = "An error occurred while calling Google Maps API: %s";
    public static final String DRIVER_WITH_DRIVER_ID = "Driver with driverId : %s";

    private DistanceConstants() {
    }
}
