package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Clinic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.model.*;

import java.util.*;

@Service
public class ClinicLocatorService {

    private final GeoApiContext context;

    public ClinicLocatorService(@Value("${google.maps.api.key}") String apiKey) {
        this.context = new GeoApiContext.Builder()
            .apiKey(apiKey)
            .build();
    }

    public Map<String, Object> findNearbyClinics(String address) throws Exception {
        // 1. Geocode the address
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        if (results.length == 0) {
            throw new Exception("Unable to geocode address");
        }
        LatLng location = results[0].geometry.location;

        // 2. Search for nearby vet clinics
        PlacesSearchResponse placesResponse = PlacesApi.nearbySearchQuery(context, location)
            .radius(5000) // 5km radius
            .type(PlaceType.VETERINARY_CARE)
            .await();

        // 3. Sort the results by distance and take the top 5
        List<PlacesSearchResult> sortedResults = Arrays.asList(placesResponse.results);
        sortedResults.sort(Comparator.comparingDouble(result -> 
            distance(location.lat, location.lng, result.geometry.location.lat, result.geometry.location.lng)));

        List<Clinic> nearestClinics = new ArrayList<>();
        for (int i = 0; i < Math.min(5, sortedResults.size()); i++) {
            PlacesSearchResult result = sortedResults.get(i);
            double dist = distance(location.lat, location.lng, result.geometry.location.lat, result.geometry.location.lng);
            nearestClinics.add(new Clinic(
                result.name,
                result.vicinity,
                result.geometry.location.lat,
                result.geometry.location.lng,
                dist
            ));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("clinics", nearestClinics);
        response.put("userLocation", Map.of("lat", location.lat, "lng", location.lng));

        return response;
    }
	//Haversine formula for distance of 2 points on a sphere
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; 
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}