package smartfridge.ingredient;

import smartfridge.storable.Storable;

public record DefaultIngredient<E extends Storable>(E item, int quantity) implements Ingredient<E> {
}
