package tech.damko.videoteka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.damko.videoteka.model.Users;

import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users,Long> {

    void deleteUserById(Long id);
    Optional<Users> getUserById(Long id);
    Optional<Users> findUserByLogin(String login);
}
