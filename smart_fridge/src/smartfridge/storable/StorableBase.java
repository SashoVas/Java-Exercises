package smartfridge.storable;

import java.time.LocalDate;

public abstract class StorableBase implements Storable {
    private final String name;
    private final LocalDate expiration;

    protected StorableBase(String name, LocalDate expiration) {
        this.name = name;
        this.expiration = expiration;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getExpiration() {
        return expiration;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isBefore(expiration);
    }
}
