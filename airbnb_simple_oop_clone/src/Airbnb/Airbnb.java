package airbnb;

import criteria.Criterion;
import places.Bookable;

public class Airbnb implements AirbnbAPI {
    private final Bookable[] accommodations;

    public Airbnb(Bookable[] accommodations) {
        this.accommodations = accommodations;
    }

    @Override
    public Bookable findAccommodationById(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }

        for (Bookable accommodation: accommodations){
            if (id.equalsIgnoreCase(accommodation.getId()))
                return accommodation;
        }
        return null;
    }

    @Override
    public double estimateTotalRevenue() {
        double sum=0;
        for (Bookable accommodation: accommodations){
            sum+=accommodation.getTotalPriceOfStay();
        }
        return sum;
    }

    @Override
    public long countBookings() {
        long count=0;
        for (Bookable accommodation: accommodations){
            count++;
        }
        return count;
    }

    private boolean fitsCriteria(Bookable accommodation,Criterion[] criteria){
        if (accommodation==null)
            return false;

        for (Criterion criterion: criteria) {
            if (!criterion.check(accommodation))
                return false;
        }

        return true;
    }
    @Override
    public Bookable[] filterAccommodations(Criterion... criteria) {
        int machingCount=0;
        for (Bookable accommodation: accommodations){
            if (fitsCriteria(accommodation,criteria))
                machingCount++;
        }
        Bookable[] result=new Bookable[machingCount];
        int current=0;
        for (Bookable accommodation: accommodations){
            if (fitsCriteria(accommodation,criteria)) {
                result[current++]=accommodation;
            }
        }
        return result;
    }
}
