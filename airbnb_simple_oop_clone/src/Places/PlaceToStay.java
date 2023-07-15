package places;

import places.location.Location;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class PlaceToStay implements Bookable{
    private final String id;
    private final double pricePerNight;
    private final Location location;
    private LocalDateTime bookedFrom=null;
    private LocalDateTime bookedTo=null;
    public PlaceToStay( double pricePerNight, Location location) {
        this.pricePerNight = pricePerNight;
        this.location = location;
        this.id=generateId();
    }
    public abstract String generateId();
    @Override
    public String getId(){
        return id;
    }
    @Override
    public Location getLocation(){
        return location;
    }

    @Override
    public boolean isBooked(){
        return bookedFrom!=null && bookedTo!=null;
    }

    @Override
    public double getPricePerNight(){
        return pricePerNight;
    }

    @Override
    public boolean book(LocalDateTime checkIn, LocalDateTime checkOut){
        if (isBooked() || checkIn==null || checkOut==null || !checkIn.isBefore(checkOut))
            return false;

        bookedFrom=checkIn;
        bookedTo=checkOut;
        return true;
    }

    private long getTotalDaysOfStay(){
        if (!isBooked())
            return 0;
        return bookedTo.until(bookedFrom, ChronoUnit.DAYS);
    }
    @Override
    public double getTotalPriceOfStay(){
        return pricePerNight*getTotalDaysOfStay();
    }
}
