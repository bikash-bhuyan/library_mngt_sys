package ai.javis.project.library_management_system.repositories;

import ai.javis.project.library_management_system.models.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookInventoryRepository extends JpaRepository<BookInventory,Integer> {
    @Query(value = "SELECT COUNT(book_id) AS count " +
            "FROM book_inventory " +
            "WHERE book_id = :bookId " +
            "AND is_available = false " +
            "GROUP BY book_id", nativeQuery = true)
    Integer countByBookIdAndIsAvailableFalse(Integer bookId);

    @Query(value = "SELECT * FROM book_inventory " +
            "WHERE book_id = :bookId AND is_available = true AND status = true LIMIT 1", nativeQuery = true)
    BookInventory findFirstAvailableByBookId(Integer bookId);
}
