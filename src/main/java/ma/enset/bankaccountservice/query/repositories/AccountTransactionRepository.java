package ma.enset.bankaccountservice.query.repositories;

import ma.enset.bankaccountservice.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository JPA pour les opérations sur les transactions
 */
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
