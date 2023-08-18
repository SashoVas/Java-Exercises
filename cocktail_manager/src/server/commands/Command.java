package server.commands;

import java.util.Arrays;

public record Command(String command, String[] arguments ) {
    public static Command of(String input){
        String[]commandParts=input.split(" ");

        return new Command(commandParts[0], Arrays.copyOfRange(commandParts,1,commandParts.length));
    }
}
