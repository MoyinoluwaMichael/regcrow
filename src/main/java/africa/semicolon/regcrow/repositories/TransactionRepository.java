package africa.semicolon.regcrow.repositories;

import africa.semicolon.regcrow.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByBuyerId(Long buyerId);
    List<Transaction> findAllBySellerId(Long buyerId);
}
