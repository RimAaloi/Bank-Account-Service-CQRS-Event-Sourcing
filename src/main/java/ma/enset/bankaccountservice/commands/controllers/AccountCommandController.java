package ma.enset.bankaccountservice.commands.controllers;

import ma.enset.bankaccountservice.commonapi.commands.CreateAccountCommand;
import ma.enset.bankaccountservice.commonapi.commands.CreditAccountCommand;
import ma.enset.bankaccountservice.commonapi.commands.DebitAccountCommand;
import ma.enset.bankaccountservice.commonapi.dto.CreateAccountDTO;
import ma.enset.bankaccountservice.commonapi.dto.CreditAccountDTO;
import ma.enset.bankaccountservice.commonapi.dto.DebitAccountDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Contrôleur REST pour les commandes de gestion des comptes bancaires
 * Expose les endpoints pour créer, créditer et débiter des comptes
 */
@RestController
@RequestMapping("/commands/account")
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    /**
     * Endpoint pour créer un nouveau compte bancaire
     */
    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountDTO request) {
        CompletableFuture<String> result = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()));
        return result;
    }

    /**
     * Endpoint pour créditer un compte existant
     */
    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO request) {
        CompletableFuture<String> result = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()));
        return result;
    }

    /**
     * Gestionnaire d'exceptions global pour ce contrôleur
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    /**
     * Endpoint pour consulter l'historique des événements d'un compte
     */
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }

    /**
     * Endpoint pour débiter un compte existant
     */
    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDTO request) {
        CompletableFuture<String> result = commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()));
        return result;
    }
}
