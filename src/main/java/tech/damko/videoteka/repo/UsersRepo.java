package tech.damko.videoteka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import tech.damko.videoteka.model.Users;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {

    Users getUserById(Long id);
    Users findByUsername(String username);
    Optional<Users> findOneByUsernameAndPassword(String username, String password);
    void deleteUserById(Long id);
}
