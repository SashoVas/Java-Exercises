package flightscanner.airport;

public record Airport(String id) {

    public Airport{
        if (id==null||id.isEmpty()||id.isBlank()){
            throw new IllegalArgumentException("Invalid id");
        }
    }
}
