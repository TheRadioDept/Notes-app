package dev.hld.Notes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.hld.Notes.dto.UserDto;
import dev.hld.Notes.dto.UserResponce;
import dev.hld.Notes.exceptions.UserNotFoundException;
import dev.hld.Notes.models.User;
import dev.hld.Notes.repositories.UserRepository;
import dev.hld.Notes.service.UserSerive;

@Service
public class UserServiceImpl implements UserSerive {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmailAddress(userDto.getEmailAddress());
        user.setUserPassword(userDto.getUserPassword());

        User newUser = userRepository.save(user);

        UserDto userResponce = new UserDto();
        userResponce.setUserId(newUser.getId());
        userResponce.setUserName(newUser.getUserName());
        userResponce.setEmailAddress(newUser.getEmailAddress());
        userResponce.setUserPassword(newUser.getUserPassword());

        return userResponce;
    }

    @Override
    public UserResponce getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);
        List<User> listofUsers = users.getContent();
        List<UserDto> content = listofUsers.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        UserResponce userResponce = new UserResponce();
        userResponce.setContent(content);
        userResponce.setPageNo(users.getNumber());
        userResponce.setPageSize(users.getSize());
        userResponce.setTotalElement(users.getTotalElements());
        userResponce.setTotalPages(users.getTotalPages());
        userResponce.setLast(users.isLast());

        return userResponce;
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be found"));
        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be found"));

        user.setUserName(userDto.getUserName());
        user.setEmailAddress(user.getEmailAddress());
        user.setUserPassword(user.getUserPassword());

        User updateUser = userRepository.save(user);
        return mapToDto(updateUser);

    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User could not be found"));
        userRepository.delete(user);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setEmailAddress(user.getEmailAddress());
        userDto.setUserPassword(user.getUserPassword());
        return userDto;
    }
}
