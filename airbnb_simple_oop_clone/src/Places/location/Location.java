package places.location;

public class Location {
    private final double x;
    private final double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double distanceTo(Location other){
        double dx=x-other.x;
        double dy=y-other.y;

        return Math.sqrt(dx*dx+dy*dy);
    }
}
