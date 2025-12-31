package ma.enset.bankaccountservice.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.bankaccountservice.commonapi.enums.AccountStatus;

import java.time.Instant;
import java.util.List;

/**
 * Entité JPA représentant un compte bancaire dans la base de lecture
 * Utilisée pour les requêtes CQRS (Query side)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    private String id;
    private double balance;
    private Instant createdAt;
    private String currency;
    private double amount;
    private AccountStatus status;
    @OneToMany(mappedBy = "account")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    List<AccountTransaction> transactions;
}
