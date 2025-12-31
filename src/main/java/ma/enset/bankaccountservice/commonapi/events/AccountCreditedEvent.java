package ma.enset.bankaccountservice.commonapi.events;

import lombok.Getter;

/**
 * Événement émis lors du crédit d'un compte bancaire
 */
public class AccountCreditedEvent extends BaseEvent {
    @Getter
    private double amount;
    @Getter
    private String currency;

    public AccountCreditedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
