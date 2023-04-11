package dev.hld.Notes.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hld.Notes.models.User;
import dev.hld.Notes.repositories.UserRepository;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    Optional<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id);
    }

    @PostMapping("/users/add")
    User addUser(@RequestParam String user_name, String email, String password, String dateOfBirth, Model model) {
        User user = new User(user_name, email, password, dateOfBirth);
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {

        return (!userRepository.existsById(id))
                ? new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.CREATED)
                : new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }
}
