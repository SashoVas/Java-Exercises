package flightscanner.flight;

import java.util.Comparator;

public class FlightByFreeSeatsComparator implements Comparator<Flight> {
    @Override
    public int compare(Flight o1, Flight o2) {
        return Integer.compare(o1.getFreeSeatsCount(),o2.getFreeSeatsCount());
    }
}
