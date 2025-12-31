package ma.enset.analyticsservice.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Requête pour récupérer l'analyse d'un compte spécifique
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAccountAnalyticsByAccountId {
    private String accountId;
}
