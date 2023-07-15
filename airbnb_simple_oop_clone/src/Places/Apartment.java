package places;

import places.location.Location;

public class Apartment extends PlaceToStay{
    private static int apartmentsCreated=0;
    public Apartment(double pricePerNight, Location location) {
        super(pricePerNight, location);
        apartmentsCreated++;
    }

    @Override
    public String generateId() {
        return "APA-"+apartmentsCreated;
    }
}
