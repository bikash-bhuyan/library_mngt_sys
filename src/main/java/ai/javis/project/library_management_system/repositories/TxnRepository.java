package ai.javis.project.library_management_system.repositories;

import ai.javis.project.library_management_system.models.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TxnRepository extends JpaRepository<Txn,Integer> {
    Optional<Txn> findByBookInventoryIdAndUserIdAndStatusTrue(Integer bookId, Integer userId);
    Optional<List<Txn>> findByStatusTrue();
    Optional<List<Txn>> findByUserIdAndStatusTrue(Integer userId);
    Optional<Txn> findByBookIdAndStatusTrue(Integer bookId);

}
