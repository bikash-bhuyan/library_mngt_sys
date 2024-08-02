package ai.javis.project.library_management_system.repositories;

import ai.javis.project.library_management_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    @Query(value = "SELECT u.id,u.email,u.membership_date,u.name,u.prime_member,u.prime_member_validity FROM users u JOIN " +
            "lending_books lb ON " +
            "u.id = lb.user_id " +
            "WHERE lb.due_date = :dueDate", nativeQuery = true)
    List<User> findUsersWithBooksDueOn(LocalDate dueDate);
}
