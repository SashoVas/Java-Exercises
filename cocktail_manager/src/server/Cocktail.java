package server;

import server.commands.Command;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record Cocktail(String name, Set<Ingredient> ingredients) {
    public static Cocktail of(Command command){
        Set<Ingredient>ingredients=new HashSet<>();

        for (int i = 0; i < command.arguments().length/2; i++) {
            ingredients.add(new Ingredient(command.arguments()[i*2],Integer.parseInt(command.arguments()[i*2 + 1])));
        }

        return new Cocktail(command.command(),ingredients);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cocktail cocktail = (Cocktail) o;
        return Objects.equals(name, cocktail.name) || Objects.equals(ingredients, cocktail.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ingredients);
    }
}
