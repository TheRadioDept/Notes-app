package dev.hld.Notes.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final long serivalVersionUID = 1;

    public UserNotFoundException(String message) {
        super(message);
    }
}
