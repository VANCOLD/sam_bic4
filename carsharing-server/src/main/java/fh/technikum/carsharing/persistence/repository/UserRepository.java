package fh.technikum.carsharing.persistence.repository;

import fh.technikum.carsharing.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT EXISTS (SELECT 1 FROM users WHERE username = :username AND password = :password)", nativeQuery = true)
    boolean findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
