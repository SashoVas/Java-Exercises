package places;

import places.location.Location;

public class Villa extends PlaceToStay{
    private static int vilasCreated=0;
    public Villa(double pricePerNight, Location location) {
        super(pricePerNight, location);
        vilasCreated++;
    }

    @Override
    public String generateId() {
        return "VIL-"+vilasCreated;
    }
}
