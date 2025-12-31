package ma.enset.bankaccountservice.commonapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO pour débiter un compte bancaire
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DebitAccountDTO {
    @Getter
    private String accountId;
    @Getter
    private double amount;
    @Getter
    private String currency;
}
