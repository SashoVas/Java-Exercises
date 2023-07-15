package places;

import places.PlaceToStay;
import places.location.Location;

public class Hotel extends PlaceToStay {
    private static int hotelsCreated=0;
    public Hotel(double pricePerNight, Location location) {
        super(pricePerNight, location);
        hotelsCreated++;
    }

    @Override
    public String generateId() {
        return "HOT-"+hotelsCreated;
    }
}
