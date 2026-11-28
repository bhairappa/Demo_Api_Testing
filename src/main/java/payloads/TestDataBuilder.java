// src/main/java/payloads/TestDataBuilder.java
package payloads;

import java.util.Arrays;
import pojos.place.AddPlaceRequest;
import pojos.place.Location;

public class TestDataBuilder {

    public static AddPlaceRequest buildAddPlacePayload(String name, String language, String address) {
        Location loc = new Location();
        loc.setLat(-38.383494);
        loc.setLng(33.427362);

        AddPlaceRequest placeRequest = new AddPlaceRequest();
        placeRequest.setLocation(loc);
        placeRequest.setAccuracy(50);
        placeRequest.setName(name);
        placeRequest.setPhone_number("(+91) 983 893 3937");
        placeRequest.setAddress(address);
        placeRequest.setTypes(Arrays.asList("shoe park", "shop"));
        placeRequest.setWebsite("http://google.com");
        placeRequest.setLanguage(language);

        return placeRequest;
    }
}