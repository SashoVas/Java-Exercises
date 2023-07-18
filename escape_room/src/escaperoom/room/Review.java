package escaperoom.room;

public record Review(int rating, String reviewText) {

    public Review {
        if (rating>10||rating<0)
            throw new IllegalArgumentException("Invalid rating");

        if (reviewText==null || reviewText.length()>200)
            throw new IllegalArgumentException("Invalid reviewText");

    }
}
