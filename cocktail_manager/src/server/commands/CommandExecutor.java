package server.commands;

import server.Cocktail;
import server.storage.CocktailStorage;

public class CommandExecutor {
    private static final String CREATE_COMMAND="create";
    private static final String CREATE_COMMAND_RESPONSE="create";
    private static final String GET_COMMAND="get";
    private static final String GET_COMMAND_ALL="all";
    private static final String GET_COMMAND_BY_NAME="by-name";
    private static final String GET_COMMAND_BY_INGREDIENT="by-ingredient";
    private static final String UNKNOWN_COMMAND_RESPONSE="get";
    private final CocktailStorage storage;

    public CommandExecutor(CocktailStorage storage) {
        this.storage = storage;
    }

    public String execute(Command command){
        try {
            switch (command.command()) {
                case CREATE_COMMAND -> {
                    storage.createCocktail(Cocktail.of(command));
                    return CREATE_COMMAND_RESPONSE;
                }
                case GET_COMMAND -> {
                    if (command.arguments().length==0){
                        return UNKNOWN_COMMAND_RESPONSE;
                    }
                    else if (command.arguments()[0].equals(GET_COMMAND_ALL)) {
                        return storage.getCocktails().toString();
                    }
                    else if(command.arguments()[0].equals(GET_COMMAND_BY_NAME)){
                        return storage.getCocktail(command.arguments()[1]).toString();
                    }
                    else if(command.arguments()[0].equals(GET_COMMAND_BY_INGREDIENT)){
                        return storage.getCocktailsWithIngredient(command.arguments()[1]).toString();
                    }
                    else {
                        return UNKNOWN_COMMAND_RESPONSE;
                    }
                }
                default->{
                    return UNKNOWN_COMMAND_RESPONSE;
                }
            }
        }
        catch(Exception e){
            return e.toString();
        }
    }
}
