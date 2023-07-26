package smartfridge.storable;

import smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public class Beverage extends StorableBase {
    protected Beverage(String name, LocalDate expiration) {
        super(name, expiration);
    }

    @Override
    public StorableType getType() {
        return StorableType.BEVERAGE;
    }
}
