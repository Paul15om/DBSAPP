package pe.com.dbs.beerapp.constants;


public final class Constant {

    private Constant() {
    }

    public static final String AUTH_TOKEN = "D0f8f007";

    public static String authToken;

    public static final String BAR_ID = "barId";

    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static String BARNAME = "barname";
    public static String NUMBER = "number";

    public static String URL_GPS = "https://maps.googleapis.com/maps/api/directions/";
    public static long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    public static long MIN_TIME_BW_UPDATES = 1000 * 60;
    public static String SENSOR = "sensor=false";
    public static String OUTPUT = "json";
}
