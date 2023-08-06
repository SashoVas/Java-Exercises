import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NetflixRecommender {
    private static final String SEARCH_WORD_REGEX = "[\\p{IsPunctuation}\\s]+";
    private static final int SENSITIVITY_THRESHOLD=10;
    private List<Content> contents;
    /**
     * Loads the dataset from the given {@code reader}.
     *
     * @param reader Reader from which the dataset can be read.
     */
    public NetflixRecommender(Reader reader) {

        try (BufferedReader bfReader=new BufferedReader(reader)){
            String columnNames=bfReader.readLine();

            contents=bfReader
                    .lines()
                    .map(Content::of)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Cant perform operation",e);
        }


    }

    /**
     * Returns all movies and shows from the dataset in undefined order as an unmodifiable List.
     * If the dataset is empty, returns an empty List.
     *
     * @return the list of all movies and shows.
     */
    public List<Content> getAllContent() {
        return List.copyOf(contents);
    }

    /**
     * Returns a list of all unique genres of movies and shows in the dataset in undefined order.
     * If the dataset is empty, returns an empty List.
     *
     * @return the list of all genres
     */
    public List<String> getAllGenres() {
        return contents.stream()
                .flatMap(el->el.genres().stream())
                .distinct()
                .toList();
    }

    /**
     * Returns the movie with the longest duration / run time. If there are two or more movies
     * with equal maximum run time, returns any of them. Shows in the dataset are not considered by this method.
     *
     * @return the movie with the longest run time
     * @throws NoSuchElementException in case there are no movies in the dataset.
     */
    public Content getTheLongestMovie() {
        return contents.stream()
                .filter(el->el.type()==ContentType.MOVIE)
                .max(Comparator.comparingInt(Content::runtime))
                .orElseThrow(()->new NoSuchElementException("No such element"));

    }

    /**
     * Returns a breakdown of content by type (movie or show).
     *
     * @return a Map with key: a ContentType and value: the set of movies or shows on the dataset, in undefined order.
     */
    public Map<ContentType, Set<Content>> groupContentByType() {
       return contents.stream()
               .collect(Collectors.groupingBy(Content::type,Collectors.toSet()));
    }

    /**
     * Returns the top N movies and shows sorted by weighed IMDB rating in descending order.
     * If there are fewer movies and shows than {@code n} in the dataset, return all of them.
     * If {@code n} is zero, returns an empty list.
     *
     * The weighed rating is calculated by the following formula:
     * Weighted Rating (WR) = (v ÷ (v + m)) × R + (m ÷ (v + m)) × C
     * where
     * R is the content's own average rating across all votes. If it has no votes, its R is 0.
     * C is the average rating of content across the dataset
     * v is the number of votes for a content
     * m is a tunable parameter: sensitivity threshold. In our algorithm, it's a constant equal to 10_000.
     *
     * Check https://stackoverflow.com/questions/1411199/what-is-a-better-way-to-sort-by-a-5-star-rating for details.
     *
     * @param n the number of the top-rated movies and shows to return
     * @return the list of the top-rated movies and shows
     * @throws IllegalArgumentException if {@code n} is negative.
     */
    public List<Content> getTopNRatedContent(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid number");
        }
        double average=contents.stream()
                .mapToDouble(Content::imdbScore)
                .average()
                .orElseThrow(IllegalArgumentException::new);

        return contents.stream()
                .sorted((el1,el2)->Double.compare(
                        calculateWeightedRating(el2,average),
                        calculateWeightedRating(el1,average)))
                .limit(n)
                .toList();
    }
    private double calculateWeightedRating(Content content, double average) {
        return (content.imdbVotes() / (content.imdbVotes() + SENSITIVITY_THRESHOLD)) * content.imdbScore() +
                (SENSITIVITY_THRESHOLD / (content.imdbVotes() + SENSITIVITY_THRESHOLD)) * average;
    }
    /**
     * Returns a list of content similar to the specified one sorted by similarity is descending order.
     * Two contents are considered similar, only if they are of the same type (movie or show).
     * The used measure of similarity is the number of genres two contents share.
     * If two contents have equal number of common genres with the specified one, their mutual oder
     * in the result is undefined.
     *
     * @param content the specified movie or show.
     * @return the sorted list of content similar to the specified one.
     */
    public List<Content> getSimilarContent(Content content) {
        return contents.stream()
                .filter(el->el.type()==content.type())
                .sorted((el1,el2)->Integer.compare(
                        similarGenresCount(el1,content),
                        similarGenresCount(el2,content)
                )).toList();
    }
    private int similarGenresCount(Content el1,Content el2){
        Set<String>el2Genres=new HashSet<>(el2.genres());
        el2Genres.retainAll(el1.genres());
        return el2Genres.size();
    }
    /**
     * Searches content by keywords in the description (case-insensitive).
     *
     * @param keywords the keywords to search for
     * @return an unmodifiable set of movies and shows whose description contains all specified keywords.
     */
    public Set<Content> getContentByKeywords(String... keywords) {
        Set<String> formattedWords= Arrays.stream(keywords)
                .map(String::toLowerCase)
                .map(String::strip)
                .collect(Collectors.toSet());

        return contents.stream()
                .filter(el-> Pattern.compile(SEARCH_WORD_REGEX)
                        .splitAsStream(el.description())
                        .map(String::toLowerCase)
                        .map(String::strip)
                        .collect(Collectors.toSet())
                        .retainAll(formattedWords)
                ).collect(Collectors.toUnmodifiableSet());
    }

}
