package dev.hld.Notes.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.hld.Notes.models.User;

public interface UserRepository extends CrudRepository<User, String> {
   User findByUserId(String userId);

    List<User> findByUserName(String userName);
}
