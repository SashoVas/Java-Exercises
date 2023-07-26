package smartfridge.ingredient;

import smartfridge.storable.Storable;

public interface Ingredient<E extends Storable> {

    /**
     * Gets the item of the ingredient.
     */
    E item();

    /**
     * Gets the quantity of the ingredient.
     */
    int quantity();

}
