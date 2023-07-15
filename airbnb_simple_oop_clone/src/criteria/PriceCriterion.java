package criteria;

import places.Bookable;

public class PriceCriterion implements Criterion{
    private final double minPrice;
    private final double maxPrice;

    public PriceCriterion(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean check(Bookable bookable) {
        if (bookable==null)
            return false;

        double bookablePrice=bookable.getPricePerNight();

        return bookablePrice>=minPrice && bookablePrice<=maxPrice;
    }
}
