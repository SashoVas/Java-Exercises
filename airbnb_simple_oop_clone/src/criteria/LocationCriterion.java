package criteria;

import places.Bookable;
import places.location.Location;

public class LocationCriterion implements Criterion {
    private final Location center;
    private final double maxRadius;

    public LocationCriterion(Location center, double maxRadius) {
        this.center = center;
        this.maxRadius = maxRadius;
    }

    @Override
    public boolean check(Bookable bookable) {
        if (bookable==null)
            return false;

        return center.distanceTo(bookable.getLocation())<=maxRadius;
    }
}
