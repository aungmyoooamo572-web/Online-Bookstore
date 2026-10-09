package org.example.bookstore.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserDao extends JpaRepository<User, UUID> {
    @Query("""
select u from User u where u.username=:usernameEmail or u.email=:usernameEmail
""")
    Optional<User> findByUsernameAndEmail(String usernameEmail);
}
