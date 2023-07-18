package escaperoom.room;

import escaperoom.rating.Ratable;

import java.util.Objects;

public class EscapeRoom implements Ratable {
    private final String name;
    private final Theme theme;
    private final Difficulty difficulty;
    private final int maxTimeToEscape;
    private final double priceToPlay;
    private final int maxReviewsCount;
    private double ratingSum=0;
    private int reviewsCount=0;
    private int currentReview=0;
    private Review[] reviews;

    public EscapeRoom(String name, Theme theme, Difficulty difficulty, int maxTimeToEscape, double priceToPlay, int maxReviewsCount) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.maxTimeToEscape = maxTimeToEscape;
        this.priceToPlay = priceToPlay;
        this.maxReviewsCount = maxReviewsCount;
        reviews=new Review[maxReviewsCount];
    }

    @Override
    public double getRating() {
        if (reviewsCount!=0)
            return ratingSum/(double)reviewsCount;
        return 0.0;
    }

    /**
     * Returns the name of the escape room.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the difficulty of the escape room.
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the maximum time to escape the room.
     */
    public int getMaxTimeToEscape() {
        return maxTimeToEscape;
    }

    /**
     * Returns all user reviews stored for this escape room, in the order they have been added.
     */
    public Review[] getReviews() {
        int reviewsCap=Math.min(maxReviewsCount,reviewsCount);
        Review[] result=new Review[reviewsCap];
        int oldestReview=maxReviewsCount<reviewsCount?currentReview:0;

        for (int i = 0; i < reviewsCap; i++) {
            result[i]=reviews[(oldestReview+i)%maxReviewsCount];
        }
        return result;
    }

    /**
     * Adds a user review for this escape room.
     * The platform keeps just the latest up to {@code maxReviewsCount} reviews and in case the capacity is full,
     * a newly added review would overwrite the oldest added one, so the platform contains
     * {@code maxReviewsCount} at maximum, at any given time. Note that, despite older reviews may have been
     * overwritten, the rating of the room averages all submitted review ratings, regardless of whether all reviews
     * themselves are still stored in the platform.
     *
     * @param review the user review to add.
     */
    public void addReview(Review review) {
        reviews[currentReview++]=review;

        if (currentReview>=maxReviewsCount)
            currentReview=0;

        ratingSum+=review.rating();
        reviewsCount++;
    }

    @Override
    public boolean equals(Object o){
        if (this==o)
            return true;
        //checks if the object is the same class as this
        if (o==null || o.getClass()!=this.getClass())
            return false;
        //cast
        return name.equals(((EscapeRoom)o).name);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
