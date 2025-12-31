package ma.enset.bankaccountservice.query.service;

import lombok.AllArgsConstructor;
import ma.enset.bankaccountservice.query.entities.Account;
import ma.enset.bankaccountservice.query.queries.GetAllAccounts;
import ma.enset.bankaccountservice.query.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des requêtes (Query side)
 * Traite les requêtes de lecture des comptes
 */
@Service
@AllArgsConstructor
public class AccountQueryHandler {
    private AccountRepository accountRepository;

    /**
     * Gestionnaire de requête pour récupérer tous les comptes
     */
    @QueryHandler
    public List<Account> on(GetAllAccounts query) {
        return accountRepository.findAll();
    }
}
