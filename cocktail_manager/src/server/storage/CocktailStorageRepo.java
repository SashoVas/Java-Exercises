package server.storage;

import server.Cocktail;
import server.storage.exceptions.CocktailAlreadyExistsException;
import server.storage.exceptions.CocktailNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CocktailStorageRepo implements CocktailStorage{

    private Map<String, Cocktail> cocktails;

    public CocktailStorageRepo() {
        this.cocktails = new HashMap<>();
    }

    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {
        if (cocktails.containsKey(cocktail.name())) {
            throw new CocktailAlreadyExistsException("Cocktail exist with same name");
        }
        if (cocktails.values().stream().anyMatch(cocktail::equals)){
            throw new CocktailAlreadyExistsException("Cocktail exist with same ingredients");
        }
        cocktails.put(cocktail.name(),cocktail);
    }

    @Override
    public Collection<Cocktail> getCocktails() {
        return cocktails.size()==0? Collections.emptyList() : cocktails.values();
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {
        return cocktails.values().stream()
                .filter(cocktail -> cocktail
                        .ingredients()
                        .stream()
                        .anyMatch(
                                ingredient -> ingredient
                                        .name()
                                        .equals(ingredientName)
                        )).toList();
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {
        if (!cocktails.containsKey(name)) {
            throw new CocktailNotFoundException("Cocktail name not found");
        }
        return cocktails.get(name);
    }
}
