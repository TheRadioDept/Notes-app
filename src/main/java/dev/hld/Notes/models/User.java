package dev.hld.Notes.models;

import java.util.UUID;

//import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(generator="system-uuid", strategy = GenerationType.AUTO)
    //@GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "userId")
    private String userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "dateOfBirth")
    private String dateOfBirth;

    public String getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User(){}

    public User(String userId, String userName, String email, String password, String dateOfBirth) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String userName, String email, String password, String dateOfBirth) {
        this(UUID.randomUUID().toString(), userName, email, password, dateOfBirth);
    }
}
