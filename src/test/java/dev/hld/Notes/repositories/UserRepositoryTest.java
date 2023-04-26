package dev.hld.Notes.repositories;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.hld.Notes.models.User;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveAll_ReturnSavedUser() {
        User user = User.builder()
                .userName("testName")
                .emailAddress("test@test")
                .userPassword("hld").build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_GetAll_ReturnMoreThanOneUser() {
        User user = User.builder()
                .userName("testName")
                .emailAddress("test@test")
                .userPassword("hld").build();
        User user2 = User.builder()
                .userName("testName1")
                .emailAddress("test1@test")
                .userPassword("hld1").build();

        userRepository.save(user);
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_FindById_ReturnUser() {
        User user = User.builder()
                .userName("testName")
                .emailAddress("test@test")
                .userPassword("hld").build();

        userRepository.save(user);

        User userList = userRepository.findById(user.getId()).get();

        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_FindByUserName_ReturnUser() {
        User user = User.builder()
                .userName("testName")
                .emailAddress("test@test")
                .userPassword("hld").build();

        userRepository.save(user);

        User userList = userRepository.findByUserName(user.getUserName()).get();

        Assertions.assertThat(userList).isNotNull();
    }

    @Test
    public void UserRepository_UpdateUser_ReturnUserNotNull() {
        User user = User.builder()
                .userName("testName")
                .emailAddress("test@test")
                .userPassword("hld").build();

        userRepository.save(user);

        User userSave = userRepository.findById(user.getId()).get();
        userSave.setUserName("newTestName");
        userSave.setEmailAddress("new@test");
        userSave.setUserPassword("newHld");

        User updateUser = userRepository.save(userSave);

        Assertions.assertThat(updateUser.getUserName()).isEqualTo("newTestName");
        Assertions.assertThat(updateUser.getEmailAddress()).isEqualTo("new@test");
        Assertions.assertThat(updateUser.getUserPassword()).isEqualTo("newHld");
    }

    @Test
    public void UserRepository_UserDelete_ReturnUserIsEmpty() {
        User user = User.builder()
                .userName("testName")
                .emailAddress("test@test")
                .userPassword("hld").build();

        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> userReturn = userRepository.findById(user.getId());

        Assertions.assertThat(userReturn).isEmpty();
    }
}
