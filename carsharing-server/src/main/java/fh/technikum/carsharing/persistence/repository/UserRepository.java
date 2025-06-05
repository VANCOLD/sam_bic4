package fh.technikum.carsharing.persistence.repository;

import fh.technikum.carsharing.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return The {@link User} object if found, null otherwise.
     */
    User findByUsername(String username);

    boolean findByUsernameAndPassword(String username, String password);
}
