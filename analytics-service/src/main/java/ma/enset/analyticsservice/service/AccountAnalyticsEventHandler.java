package ma.enset.analyticsservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.bankaccountservice.commonapi.events.AccountCreatedEvent;
import ma.enset.bankaccountservice.commonapi.events.AccountCreditedEvent;
import ma.enset.bankaccountservice.commonapi.events.AccountDebitedEvent;
import ma.enset.analyticsservice.entities.AccountAnalytics;
import ma.enset.analyticsservice.queries.*;
import ma.enset.analyticsservice.repository.AccountAnalyticsRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service de gestion des événements pour les analyses de comptes
 * Écoute les événements de création, crédit et débit pour mettre à jour les
 * statistiques
 */
@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class AccountAnalyticsEventHandler {
    private AccountAnalyticsRepository accountAnalyticsRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    /**
     * Gestionnaire d'événement pour la création de compte
     * Initialise les statistiques d'analyse pour le nouveau compte
     */
    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("========================================");
        log.info(">>> Événement de création de compte reçu pour analyse");
        AccountAnalytics accountAnalytics = AccountAnalytics.builder()
                .accountId(event.getId())
                .totalCredit(0)
                .totalDebit(0)
                .balance(event.getInitialBalance())
                .totalNumberofCredits(0)
                .totalNumberofDebits(0)
                .build();
        accountAnalyticsRepository.save(accountAnalytics);
        log.info(">>> Analyse initialisée pour le compte: {}", event.getId());
    }

    /**
     * Gestionnaire d'événement pour le débit de compte
     * Met à jour les statistiques de débit
     */
    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("========================================");
        log.info(">>> Événement de débit reçu pour analyse");
        AccountAnalytics accountAnalytics = accountAnalyticsRepository.findByAccountId(event.getId().toString());
        if (accountAnalytics != null) {
            accountAnalytics.setBalance(accountAnalytics.getBalance() - event.getAmount());
            accountAnalytics.setTotalDebit(accountAnalytics.getTotalDebit() + event.getAmount());
            accountAnalytics.setTotalNumberofDebits(accountAnalytics.getTotalNumberofDebits() + 1);
            accountAnalyticsRepository.save(accountAnalytics);
            queryUpdateEmitter.emit(GetAllAccountAnalyticsByAccountId.class,
                    query -> query.getAccountId().equals(accountAnalytics.getAccountId()),
                    accountAnalytics);
            log.info(">>> Analyse mise à jour pour le compte: {}", event.getId());
        } else {
            log.error(">>> Analyse non trouvée pour le compte: {}", event.getId());
        }
    }

    /**
     * Gestionnaire d'événement pour le crédit de compte
     * Met à jour les statistiques de crédit
     */
    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("========================================");
        log.info(">>> Événement de crédit reçu pour analyse");
        AccountAnalytics accountAnalytics = accountAnalyticsRepository.findByAccountId(event.getId().toString());
        if (accountAnalytics != null) {
            accountAnalytics.setBalance(accountAnalytics.getBalance() + event.getAmount());
            accountAnalytics.setTotalCredit(accountAnalytics.getTotalCredit() + event.getAmount());
            accountAnalytics.setTotalNumberofCredits(accountAnalytics.getTotalNumberofCredits() + 1);
            accountAnalyticsRepository.save(accountAnalytics);
            queryUpdateEmitter.emit(GetAllAccountAnalyticsByAccountId.class,
                    query -> query.getAccountId().equals(accountAnalytics.getAccountId()),
                    accountAnalytics);
            log.info(">>> Analyse mise à jour pour le compte: {}", event.getId());
        } else {
            log.error(">>> Analyse non trouvée pour le compte: {}", event.getId());
        }
    }

    /**
     * Gestionnaire de requête pour récupérer toutes les analyses
     */
    @QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalytics query) {
        return accountAnalyticsRepository.findAll();
    }

    /**
     * Gestionnaire de requête pour récupérer l'analyse d'un compte spécifique
     */
    @QueryHandler
    public AccountAnalytics on(GetAllAccountAnalyticsByAccountId query) {
        return accountAnalyticsRepository.findByAccountId(query.getAccountId());
    }
}
