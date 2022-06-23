package hello.entity;

import java.time.Instant;

public class User {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    Integer id;
    String username;
    String avatar;

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    String encryptedPassword;
    Instant createdAt;
    Instant updatedAt;

    public User(Integer id, String username, String encryptedPassword) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.avatar = "";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }


}
