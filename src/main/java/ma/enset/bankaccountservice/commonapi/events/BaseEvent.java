package ma.enset.bankaccountservice.commonapi.events;

import lombok.Getter;

/**
 * Classe de base pour tous les événements du domaine
 */
public class BaseEvent<T> {
    @Getter
    private T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
