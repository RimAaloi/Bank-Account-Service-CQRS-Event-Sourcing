package ma.enset.analyticsservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant les statistiques d'analyse d'un compte bancaire
 * Stocke les totaux des crédits, débits et le nombre d'opérations
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private double balance;
    private double totalDebit;
    private double totalCredit;
    private int totalNumberofDebits;
    private int totalNumberofCredits;
}
