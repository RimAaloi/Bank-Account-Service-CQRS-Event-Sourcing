package ma.enset.analyticsservice.repository;

import ma.enset.analyticsservice.entities.AccountAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour les opérations sur les analyses de comptes
 */
public interface AccountAnalyticsRepository extends JpaRepository<AccountAnalytics, Long> {
    AccountAnalytics findByAccountId(String accountId);
}
