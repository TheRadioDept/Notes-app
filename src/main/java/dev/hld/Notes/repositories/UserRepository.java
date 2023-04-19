package dev.hld.Notes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hld.Notes.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
