package smartfridge.storable;

import smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public class Food extends StorableBase {
    protected Food(String name, LocalDate expiration) {
        super(name, expiration);
    }

    @Override
    public StorableType getType() {
        return StorableType.FOOD;
    }
}
