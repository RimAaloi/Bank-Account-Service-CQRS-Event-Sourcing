package ma.enset.bankaccountservice.query.controllers;

import ma.enset.bankaccountservice.query.entities.Account;
import ma.enset.bankaccountservice.query.queries.GetAllAccounts;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Contrôleur REST pour les requêtes de consultation des comptes
 * Expose les endpoints de lecture (Query side)
 */
@RestController
@RequestMapping("/query/accounts")
@CrossOrigin("*")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    /**
     * Endpoint pour récupérer la liste de tous les comptes
     */
    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts() {
        CompletableFuture<List<Account>> result = queryGateway.query(new GetAllAccounts(),
                ResponseTypes.multipleInstancesOf(Account.class));
        return result;
    }
}
