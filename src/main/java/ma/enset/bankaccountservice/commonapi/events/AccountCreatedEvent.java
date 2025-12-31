package ma.enset.bankaccountservice.commonapi.events;

import lombok.Getter;
import ma.enset.bankaccountservice.commonapi.enums.AccountStatus;

/**
 * Événement émis lors de la création d'un compte bancaire
 */
public class AccountCreatedEvent extends BaseEvent<String> {

    @Getter
    private String currency;
    @Getter
    private double initialBalance;
    @Getter
    private AccountStatus status;

    public AccountCreatedEvent(String id, String currency, double initialBalance, AccountStatus status) {
        super(id);
        this.currency = currency;
        this.initialBalance = initialBalance;
        this.status = status;
    }
}
