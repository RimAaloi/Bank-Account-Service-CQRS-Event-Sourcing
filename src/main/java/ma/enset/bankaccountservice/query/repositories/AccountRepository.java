package ma.enset.bankaccountservice.query.repositories;

import ma.enset.bankaccountservice.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour les opérations sur les comptes
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}
