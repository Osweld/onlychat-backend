package german.dev.onlychatbackend.user.repository;

import german.dev.onlychatbackend.user.entity.User;
import german.dev.onlychatbackend.user.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        @Query("SELECT " +
                        "u.id as id," +
                        " u.username as username," +
                        " u.email as email," +
                        " u.createdAt as createdAt," +
                        " r.name as rol," +
                        " us.name as userStatus" +
                        " FROM User u JOIN u.rol r JOIN u.userStatus us WHERE u.id = :id")
        Optional<UserProjection> findUserById(Long id);

        @Query("SELECT " +
                        "u.id as id," +
                        " u.username as username," +
                        " u.email as email," +
                        " u.createdAt as createdAt," +
                        " r.name as rol," +
                        " us.name as userStatus" +
                        " FROM User u JOIN u.rol r JOIN u.userStatus us WHERE u.username = :username")
        Optional<UserProjection> findUserbyUsername(String username);

        @Query("SELECT " +
                        "u.id as id," +
                        " u.username as username," +
                        " u.email as email," +
                        " u.createdAt as createdAt," +
                        " r.name as rol," +
                        " us.name as userStatus" +
                        " FROM User u JOIN u.rol r JOIN u.userStatus us WHERE LOWER(u.username) LIKE LOWER(CONCAT('%',:username,'%'))")
        Page<UserProjection> searchUserByUsername(String username, Pageable pageable);

        @Query("SELECT " +
                        "u.id as id," +
                        " u.username as username," +
                        " u.email as email," +
                        " u.createdAt as createdAt," +
                        " r.name as rol," +
                        " us.name as userStatus" +
                        " FROM User u JOIN u.rol r JOIN u.userStatus us")
        Page<UserProjection> findAllUsers(Pageable pageable);


        Boolean existsByUsername(String username);

        Optional<User> findByUsername(String username);

}
