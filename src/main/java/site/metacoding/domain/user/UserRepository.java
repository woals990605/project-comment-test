package site.metacoding.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    // = executeQuery()
    @Query(value = "SELECT * FROM user WHERE id=:id AND password=:password", nativeQuery = true)
    User mLogin(@Param("id") String id, @Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE id= :id", nativeQuery = true)
    User mUsernameSameCheck(@Param("id") String id);

    @Query(value = "SELECT * FROM user WHERE email= :email", nativeQuery = true)
    User mEmailSameCheck(@Param("email") String email);

    @Query(value = "SELECT * FROM user WHERE name = :name AND id = :id AND email = :email", nativeQuery = true)
    Optional<User> findPassword(@Param("name") String name, @Param("id") String id, @Param("email") String email);
}
