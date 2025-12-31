package ma.enset.bankaccountservice.commonapi.dto;

/**
 * DTO pour la création d'un compte bancaire
 * Utilise un record Java pour plus de concision
 */
public record CreateAccountDTO(String currency, double initialBalance) {
}
