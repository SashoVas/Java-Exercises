package smartfridge.storable;

import smartfridge.storable.type.StorableType;

import java.time.LocalDate;

public class Other extends StorableBase{
    protected Other(String name, LocalDate expiration) {
        super(name, expiration);
    }

    @Override
    public StorableType getType() {
        return StorableType.OTHER;
    }
}
