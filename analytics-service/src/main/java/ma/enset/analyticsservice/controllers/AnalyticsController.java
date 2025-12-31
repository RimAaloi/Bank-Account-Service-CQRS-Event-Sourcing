package ma.enset.analyticsservice.controllers;

import ma.enset.analyticsservice.entities.AccountAnalytics;
import ma.enset.analyticsservice.queries.GetAllAccountAnalytics;
import ma.enset.analyticsservice.queries.GetAllAccountAnalyticsByAccountId;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Contrôleur REST pour les analyses de comptes bancaires
 * Expose les endpoints de consultation des statistiques
 */
@RestController
@Slf4j
public class AnalyticsController {
    private QueryGateway queryGateway;

    public AnalyticsController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    /**
     * Endpoint pour récupérer toutes les analyses de comptes
     */
    @GetMapping("/query/accountAnalytics")
    public CompletableFuture<List<AccountAnalytics>> accountAnalytics() {
        return queryGateway.query(new GetAllAccountAnalytics(),
                ResponseTypes.multipleInstancesOf(AccountAnalytics.class));
    }

    /**
     * Endpoint pour récupérer l'analyse d'un compte spécifique
     */
    @GetMapping("/query/accountAnalytics/{accountId}")
    public CompletableFuture<AccountAnalytics> getAccountAnalyticsById(@PathVariable String accountId) {
        return queryGateway.query(new GetAllAccountAnalyticsByAccountId(accountId),
                ResponseTypes.instanceOf(AccountAnalytics.class));
    }

    /**
     * Endpoint SSE pour suivre les mises à jour en temps réel d'une analyse de
     * compte
     */
    @GetMapping(value = "/query/accountAnalytics/{accountId}/watch", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountAnalytics> watchAccountAnalyticsById(@PathVariable String accountId) {
        SubscriptionQueryResult<AccountAnalytics, AccountAnalytics> subscriptionQueryResult = queryGateway
                .subscriptionQuery(
                        new GetAllAccountAnalyticsByAccountId(accountId),
                        ResponseTypes.instanceOf(AccountAnalytics.class),
                        ResponseTypes.instanceOf(AccountAnalytics.class));
        return subscriptionQueryResult.initialResult().concatWith(subscriptionQueryResult.updates());
    }
}
