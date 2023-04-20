package dev.hld.Notes.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import dev.hld.Notes.dto.NoteDto;
import dev.hld.Notes.dto.UserDto;
import dev.hld.Notes.dto.UserResponce;
import dev.hld.Notes.models.Note;
import dev.hld.Notes.models.User;
import dev.hld.Notes.service.UserSerive;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserSerive userSerive;

    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private Note note;
    private UserDto userDto;
    private NoteDto noteDto;

    @BeforeEach
    public void init() {
        user = User.builder().userName("testName").emailAddress("test@test").userPassword("hld").build();
        userDto = UserDto.builder().userName("testName").emailAddress("test@test").userPassword("hld").build();
        note = Note.builder().topic("testTopic").noteBody("testNote").build();
        noteDto = NoteDto.builder().topic("testTopic").noteBody("testNote").build();
    }

    @Test
    public void UserController_CreateUser_ReturnCreated() throws Exception {
        given(userSerive.createUser(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", CoreMatchers.is(userDto.getUserName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress", CoreMatchers.is(userDto.getEmailAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userPassword", CoreMatchers.is(userDto.getUserPassword())));
    }

    @Test
    public void UserController_GetAllUsers_ReturnResponseDto() throws Exception {
        UserResponce responseDto = UserResponce.builder().pageSize(10).last(true).pageNo(1)
                .content(Arrays.asList(userDto)).build();
        when(userSerive.getAllUsers(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()",
                        CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void UserController_UserDetails_ReturnUserDto() throws Exception {
        int userId = 1;
        when(userSerive.getUserById(userId)).thenReturn(userDto);

        ResultActions response = mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.userName", CoreMatchers.is(userDto.getUserName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress", CoreMatchers.is(userDto.getEmailAddress())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userPassword", CoreMatchers.is(userDto.getUserPassword())));
    }

    @Test
    public void UserControll_UpdateUser_ReturnUserDto() throws Exception {
        int userId = 1;
        when(userSerive.updateUser(userDto, userId)).thenReturn(userDto);

        ResultActions response = mockMvc.perform(put("/user/1/update")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.userName", CoreMatchers.is(userDto.getUserName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.emailAddress", CoreMatchers.is(userDto.getEmailAddress())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userPassword", CoreMatchers.is(userDto.getUserPassword())));
    }

    @Test
    public void UserControll_DeleteUser_ReturnString() throws Exception {
        int userId = 1;
        doNothing().when(userSerive).deleteUser(userId);

        ResultActions response = mockMvc.perform(delete("/user/1/delete")
            .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
