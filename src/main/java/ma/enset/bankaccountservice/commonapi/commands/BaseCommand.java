package ma.enset.bankaccountservice.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * Classe de base pour toutes les commandes
 * Contient l'identifiant de l'agrégat cible
 */
public class BaseCommand<T> {
    @TargetAggregateIdentifier
    @Getter
    T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
