package ai.javis.project.library_management_system.repositories;

import ai.javis.project.library_management_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
