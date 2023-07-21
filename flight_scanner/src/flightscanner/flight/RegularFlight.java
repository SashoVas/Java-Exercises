package flightscanner.flight;

import flightscanner.airport.Airport;
import flightscanner.exception.FlightCapacityExceededException;
import flightscanner.exception.InvalidFlightException;
import flightscanner.passenger.Passenger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RegularFlight implements Flight{
    private final String flightId;
    private final Airport from;
    private final Airport to;
    private final int totalCapacity;
    private Set<Passenger> passengers;

    private RegularFlight(String flightId, Airport from, Airport to, int totalCapacity) {
        this.flightId = flightId;
        this.from = from;
        this.to = to;
        this.totalCapacity = totalCapacity;
        passengers= HashSet.newHashSet(totalCapacity);
    }
    public static RegularFlight of(String flightId, Airport from, Airport to, int totalCapacity){
        if (flightId==null||flightId.isBlank()||flightId.isEmpty())
        {
            throw new IllegalArgumentException("Invalid flight id");
        }
        if (from==null){
            throw new IllegalArgumentException("Invalid value for from");
        }
        if (to==null){
            throw new IllegalArgumentException("Invalid value for to");
        }
        if (from.equals(to)){
            throw new IllegalArgumentException("to equals from");
        }
        if (totalCapacity<=0){
            throw new InvalidFlightException("Invalid capacity");
        }
        return new RegularFlight(flightId,from,to,totalCapacity);
    }
    @Override
    public Airport getFrom() {
        return from;
    }

    @Override
    public Airport getTo() {
        return to;
    }

    @Override
    public void addPassenger(Passenger passenger) throws FlightCapacityExceededException {
        if (passenger==null){
            throw new IllegalArgumentException("Invalid passenger");
        }
        if (passengers.size()>=totalCapacity){
            throw new FlightCapacityExceededException("Capacity exceeded");
        }
        passengers.add(passenger);
    }

    @Override
    public void addPassengers(Collection<Passenger> passengers) throws FlightCapacityExceededException {
        if (passengers==null){
            throw new IllegalArgumentException("Invalid passenger");
        }
        if (this.passengers.size()+passengers.size()>totalCapacity){
            throw new FlightCapacityExceededException("Capacity exceeded");
        }
        this.passengers.addAll(passengers);
    }

    @Override
    public Collection<Passenger> getAllPassengers() {
        return Set.copyOf(passengers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularFlight that = (RegularFlight) o;
        return Objects.equals(flightId, that.flightId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightId);
    }

    @Override
    public int getFreeSeatsCount() {
        return totalCapacity-passengers.size();
    }

}
