package pe.com.dbs.beerapp;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectionsJSONParser {

    private static final String ROUTES = "routes";
    private static final String STEPS = "steps";
    private static final String LEGS = "legs";
    private static final String POLYLINE = "polyline";
    private static final String POINTS = "points";

    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lng";

    public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

        List<List<HashMap<String, String>>> routes = new ArrayList<>();

        try {

            JSONArray jRoutes = jObject.getJSONArray(ROUTES);

            for (int i = 0; i < jRoutes.length(); i++) {
                JSONArray jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray(LEGS);
                List path = new ArrayList<>();

                for (int j = 0; j < jLegs.length(); j++) {
                    JSONArray jSteps = ((JSONObject) jLegs.get(j)).getJSONArray(STEPS);

                    for (int k = 0; k < jSteps.length(); k++) {
                        String polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get(POLYLINE)).get(POINTS);
                        List<LatLng> list = decodePoly(polyline);

                        for (int l = 0; l < list.size(); l++) {
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put(LATITUDE, Double.toString(list.get(l).latitude));
                            hm.put(LONGITUDE, Double.toString(list.get(l).longitude));
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return routes;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
    
}
