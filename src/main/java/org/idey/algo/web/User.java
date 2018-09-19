package org.idey.algo.web;

import java.time.Instant;
import java.util.Objects;

public class User {
    private int userId;
    private Instant currentTime;

    public User(int userId) {
        this.userId = userId;
        currentTime = Instant.now();
    }

    public int getUserId() {
        return userId;
    }

    public Instant getCurrentTime() {
        return currentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
