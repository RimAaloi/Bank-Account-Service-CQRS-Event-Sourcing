package ma.enset.bankaccountservice.commands.aggregate;

import lombok.extern.slf4j.Slf4j;
import ma.enset.bankaccountservice.commonapi.commands.CreateAccountCommand;
import ma.enset.bankaccountservice.commonapi.commands.CreditAccountCommand;
import ma.enset.bankaccountservice.commonapi.commands.DebitAccountCommand;
import ma.enset.bankaccountservice.commonapi.enums.AccountStatus;
import ma.enset.bankaccountservice.commonapi.events.AccountCreatedEvent;
import ma.enset.bankaccountservice.commonapi.events.AccountCreditedEvent;
import ma.enset.bankaccountservice.commonapi.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

/**
 * Agrégat représentant un compte bancaire
 * Gère les commandes et applique les événements pour l'Event Sourcing
 */
@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    // Constructeur par défaut requis par Axon
    public AccountAggregate() {
    }

    /**
     * Gestionnaire de la commande de création de compte
     */
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        log.info(">>> Commande de création de compte reçue");
        log.info(">>> ID du compte: {}", command.getId());
        if (command.getInitialBalance() < 0)
            throw new RuntimeException("Erreur: Le solde ne peut pas être négatif");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getCurrency(),
                command.getInitialBalance(),
                AccountStatus.CREATED));
    }

    /**
     * Gestionnaire d'événement pour la création de compte
     */
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.currency = event.getCurrency();
        this.status = event.getStatus();
    }

    /**
     * Gestionnaire de la commande de crédit
     */
    @CommandHandler
    public void handle(CreditAccountCommand command) {
        log.info(">>> Commande de crédit reçue");
        if (command.getAmount() < 0)
            throw new RuntimeException("Erreur: Le montant ne peut pas être négatif");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()));
    }

    /**
     * Gestionnaire d'événement pour le crédit
     */
    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    /**
     * Gestionnaire de la commande de débit
     */
    @CommandHandler
    public void handle(DebitAccountCommand command) {
        log.info(">>> Commande de débit reçue");
        if (command.getAmount() > this.balance)
            throw new RuntimeException("Erreur: Solde insuffisant pour effectuer cette opération");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()));
    }

    /**
     * Gestionnaire d'événement pour le débit
     */
    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }
}
