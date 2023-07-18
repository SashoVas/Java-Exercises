package escaperoom.team;

import escaperoom.rating.Ratable;

public class Team implements Ratable {
    private final String name;
    private final TeamMember[] members;
    private double rating=0;

    private Team(String name, TeamMember[] members) {
        this.name = name;
        this.members = members;
    }
    public static Team of(String name, TeamMember[] members){
        return new Team(name, members);
    }
    public void updateRating(int points) {
        if (points<0)
            throw new IllegalArgumentException("Invalid points");

        rating +=points;
    }

    /**
     * Returns the team name.
     */
    public String getName() {
        return name;
    }

    @Override
    public double getRating() {
        return rating;
    }
}
