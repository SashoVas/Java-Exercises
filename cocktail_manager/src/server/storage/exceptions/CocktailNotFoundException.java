package server.storage.exceptions;

public class CocktailNotFoundException extends Exception{
    public CocktailNotFoundException(String message) {
        super(message);
    }

    public CocktailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
