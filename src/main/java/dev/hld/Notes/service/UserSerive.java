package dev.hld.Notes.service;

import dev.hld.Notes.dto.UserDto;
import dev.hld.Notes.dto.UserResponce;

public interface UserSerive {
    UserDto createUser(UserDto UserDto);

    UserResponce getAllUsers(int pageNo, int pageSize);

    UserDto getUserById(int userId);

    UserDto updateUser(UserDto userDto, int userId);

    void deleteUser(int userId);
}
