package dev.hld.Notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.hld.Notes.dto.UserDto;
import dev.hld.Notes.dto.UserResponce;
import dev.hld.Notes.models.User;
import dev.hld.Notes.repositories.UserRepository;
import dev.hld.Notes.service.UserSerive;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private UserSerive userSerive;

    public UserController(UserSerive userSerive) {
        this.userSerive = userSerive;
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponce> getUser(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(userSerive.getAllUsers(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/user")
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> userDetail(@PathVariable("id") int userId) {
        return ResponseEntity.ok(userSerive.getUserById(userId));
    }

    @PostMapping("/user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userSerive.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") int userId) {
        UserDto response = userSerive.updateUser(userDto, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int userId) {
        userSerive.deleteUser(userId);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}
