package ai.javis.project.library_management_system.repositories;

import ai.javis.project.library_management_system.models.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TxnRepository extends JpaRepository<Txn,Integer> {

    Optional<Txn> findByBookInventoryIdAndUserIdAndStatusTrue(Integer bookId, Integer userId);

    Optional<List<Txn>> findByStatusTrue();

    Optional<List<Txn>> findByUserIdAndStatusTrue(Integer userId);

    @Query(value = "SELECT * FROM lending_books " +
            "WHERE book_id = :bookId " +
            "AND status = true " +
            " ORDER BY due_date LIMIT 1", nativeQuery = true)
    Txn findByBookIdAndStatusTrueAndEarliestAvailable(Integer bookId);

}
