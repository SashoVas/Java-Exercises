import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record Content(String id, String title, ContentType type, String description, int releaseYear, int runtime, List<String> genres, int seasons, String imdbId, double imdbScore, double imdbVotes) {

    public static Content of(String line){
        String[] contents=line.split(",");
        String id=contents[0];
        String title=contents[1];
        ContentType contentType=ContentType.valueOf(contents[2]);
        String description=contents[3];
        int releaseYear=Integer.parseInt(contents[4]);
        int runtime=Integer.parseInt(contents[5]);
        List<String>genres=contents[6].length()>4?List.of(contents[6].substring(2,contents[6].length()-3).split("'; '")): Collections.emptyList();
        int seasons=Integer.parseInt(contents[7]);
        String imdbId=contents[8];
        double imdbScore=Double.parseDouble(contents[9]);
        double imdbVotes=Double.parseDouble(contents[10]);
        return new Content(id,title,contentType,description,releaseYear,runtime,genres,seasons,imdbId,imdbScore,imdbVotes  );
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
