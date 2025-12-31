package ma.enset.bankaccountservice.commonapi.commands;

import lombok.Getter;

/**
 * Commande pour débiter un compte bancaire existant
 */
public class DebitAccountCommand extends BaseCommand<String> {
    @Getter
    private double amount;
    @Getter
    private String currency;

    public DebitAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}
