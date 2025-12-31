package ma.enset.bankaccountservice.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.bankaccountservice.commonapi.events.AccountCreatedEvent;
import ma.enset.bankaccountservice.query.entities.Account;
import ma.enset.bankaccountservice.query.repositories.AccountRepository;
import ma.enset.bankaccountservice.query.repositories.AccountTransactionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des événements pour le côté requête (Query side)
 * Écoute les événements et met à jour la base de lecture
 */
@Service
@AllArgsConstructor
@Slf4j
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;

    /**
     * Gestionnaire d'événement pour la création de compte
     * Met à jour la projection du compte dans la base de lecture
     */
    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent, EventMessage<AccountCreatedEvent> eventMessage) {
        log.info("========================================");
        log.info(">>> Événement de création de compte reçu");
        Account account = new Account();
        account.setId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getInitialBalance());
        account.setCurrency(accountCreatedEvent.getCurrency());
        account.setStatus(accountCreatedEvent.getStatus());
        account.setCreatedAt(eventMessage.getTimestamp());
        accountRepository.save(account);
        log.info(">>> Compte créé avec succès: {}", account.getId());
    }
}
