package flightscanner;

import flightscanner.airport.Airport;
import flightscanner.flight.Flight;
import flightscanner.flight.FlightByDestinationComparator;
import flightscanner.flight.FlightByFreeSeatsComparator;

import java.util.*;

public class FlightScanner implements FlightScannerAPI{
    private Map<Airport, Set<Flight>>flights;
    public FlightScanner(){
        flights=new HashMap<>();
    }
    @Override
    public void add(Flight flight) {
        if (flight==null){
            throw new IllegalArgumentException("Invalid flight");
        }
        flights.putIfAbsent(flight.getFrom(),new HashSet<>());
        flights.get(flight.getFrom()).add(flight);
    }

    @Override
    public void addAll(Collection<Flight> flights) {
        if (flights==null){
            throw new IllegalArgumentException("Invalid flights");
        }
        for (var flight:flights){
            add(flight);
        }
    }

    private List<Flight> getPath(Airport from,Airport to,Map<Airport,Flight>parents){
        List<Flight> result=new ArrayList<Flight>();
        while(!to.equals(from)){
            var flight=parents.get(to);
            result.add(flight);
            to=flight.getFrom();
        }

        Collections.reverse(result);
        return result;
    }
    @Override
    public List<Flight> searchFlights(Airport from, Airport to) {

        if (from==null|| to==null){
            throw new IllegalArgumentException("Invalid airports");
        }

        Queue<Airport> queue=new ArrayDeque<>();
        Set<Airport>visited=new HashSet<>();
        Map<Airport,Flight>parents=new HashMap<>();

        visited.add(from);
        queue.add(from);
        while(!queue.isEmpty()){
            Airport current=queue.poll();
            var toAdd=flights.get(current);

            if (toAdd==null){
                continue;
            }

            for (var flight:toAdd){
                if (!visited.contains(flight)){

                    queue.add(flight.getTo());
                    parents.put(flight.getTo(),flight);
                    visited.add(flight.getTo());

                    if (flight.getTo().equals(to)){
                        return getPath(from,to,parents);
                    }
                }

            }
        }
        return Collections.emptyList();
    }
    private List<Flight> sortFlightsBy(Airport from,Comparator<Flight>comparator){
        if (from==null){
            throw new IllegalArgumentException("Invalid airport");
        }
        var flight=flights.get(from);
        if (flight==null){
            throw new IllegalArgumentException("Invalid location");
        }
        var result=new ArrayList<>( flights.get(from));
        Collections.sort(result,comparator);
        return List.copyOf(result);
    }
    @Override
    public List<Flight> getFlightsSortedByFreeSeats(Airport from) {
        return sortFlightsBy(from,new FlightByFreeSeatsComparator());
    }

    @Override
    public List<Flight> getFlightsSortedByDestination(Airport from) {
        return sortFlightsBy(from,new FlightByDestinationComparator());
    }
}
