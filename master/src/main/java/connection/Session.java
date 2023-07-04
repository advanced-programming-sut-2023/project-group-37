package connection;

import model.user.User;

import java.time.LocalDateTime;

public class Session {
    private User user;
    private final String lastLoginTime;
    private final LocalDateTime expirationTime;

    public Session(User user) {
        this.user = user;
        this.lastLoginTime = LocalDateTime.now().toString();
        this.expirationTime = LocalDateTime.parse(this.lastLoginTime).plusHours(1);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLastLoginTime() {
        return this.lastLoginTime;
    }

    public LocalDateTime getExpirationTime() {
        return this.expirationTime;
    }

}
