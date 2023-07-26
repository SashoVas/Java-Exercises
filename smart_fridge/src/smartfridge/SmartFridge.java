package smartfridge;

import smartfridge.exceptions.FridgeCapacityExceededException;
import smartfridge.exceptions.InsufficientQuantityException;
import smartfridge.ingredient.DefaultIngredient;
import smartfridge.ingredient.Ingredient;
import smartfridge.recipe.Recipe;
import smartfridge.storable.Storable;
import smartfridge.storable.StorableByExpirationComparator;

import java.util.*;

public class SmartFridge implements SmartFridgeAPI {
    private final int totalCapacity;
    private int itemsCount=0;
    private final Map<String, Queue<Storable>> items;

    public SmartFridge(int totalCapacity) {
        this.totalCapacity = totalCapacity;
        items=HashMap.newHashMap(totalCapacity);
    }

    @Override
    public <E extends Storable> void store(E item, int quantity) throws FridgeCapacityExceededException {
        if (item==null){
            throw new IllegalArgumentException("Invalid item");
        }
        if (quantity<=0){
            throw new IllegalArgumentException("Invalid quantity");
        }
        if (totalCapacity==itemsCount){
            throw new FridgeCapacityExceededException("Not enough space in the fridge");
        }
        items.putIfAbsent(item.getName(),new PriorityQueue<>(new StorableByExpirationComparator()));
        for (int i = 0; i < quantity; i++) {
            items.get(item.getName()).add(item);
        }
        itemsCount+=quantity;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName) {
        if (itemName==null||itemName.isBlank()|| itemName.isEmpty()){
            throw new IllegalArgumentException("Invalid item name");
        }
        if (!items.containsKey(itemName)){
            return Collections.emptyList();
        }

        List<Storable>result=new ArrayList<>(items.get(itemName));
        items.remove(itemName);
        itemsCount-=result.size();

        return result;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException {
        if (itemName==null||itemName.isBlank()|| itemName.isEmpty()){
            throw new IllegalArgumentException("Invalid item name");
        }
        if (!items.containsKey(itemName)){
            return Collections.emptyList();
        }
        if (items.get(itemName).size()<quantity){
            throw new  InsufficientQuantityException("Not enough items");
        }
        itemsCount-=quantity;
        List<Storable>result=new ArrayList<>();
        Queue<Storable> all=items.get(itemName);

        for (int i = 0; i < quantity; i++) {
            all.add(all.poll());
        }
        if (quantity==all.size()){
            items.remove(itemName);
        }
        return result;
    }

    @Override
    public int getQuantityOfItem(String itemName) {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException("Invalid item name");
        }

        Queue<Storable>all=items.get(itemName);

        return all==null?0:all.size();
    }

    @Override
    public Iterator<Ingredient<? extends Storable>> getMissingIngredientsFromRecipe(Recipe recipe) {
        if (recipe==null){
            throw new IllegalArgumentException("Invalid recipe");
        }

        Set<Ingredient<?>> ingredients=recipe.getIngredients();
        List<Ingredient<?>> result=new ArrayList<>();

        for (Ingredient<?> ingredient:ingredients) {
            if (!items.containsKey(ingredient.item().getName())){
                result.add(ingredient);
            }

            Queue<Storable>foods=items.get(ingredient.item().getName());
            int avalable =0;
            for (Storable food:foods){
                avalable +=food.isExpired()?1:0;
            }
            if (ingredient.quantity()>avalable){
                result.add(new DefaultIngredient<>(ingredient.item(),
                        ingredient.quantity()-avalable));
            }
        }

        return result.iterator();
    }

    @Override
    public List<? extends Storable> removeExpired() {
        List<Storable>result=new ArrayList<>();

        for(Queue<Storable> currentQueue:items.values()){
            for (Iterator<Storable>it=currentQueue.iterator();it.hasNext();){
                Storable current=it.next();
                if (current.isExpired()){
                    result.add(current);
                    it.remove();
                    itemsCount--;
                }
            }
        }

        return result;
    }
}
