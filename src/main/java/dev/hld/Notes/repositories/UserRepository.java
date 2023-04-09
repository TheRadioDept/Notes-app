package dev.hld.Notes.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.hld.Notes.models.User;

public interface UserRepository extends CrudRepository<User, String> {}
