package converters;

public interface Converter {
    boolean isApplicable(String line);
    String apply(String line);
}
