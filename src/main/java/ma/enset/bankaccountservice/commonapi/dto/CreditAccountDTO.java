package ma.enset.bankaccountservice.commonapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO pour créditer un compte bancaire
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreditAccountDTO {
    @Getter
    private String accountId;
    @Getter
    private double amount;
    @Getter
    private String currency;
}
