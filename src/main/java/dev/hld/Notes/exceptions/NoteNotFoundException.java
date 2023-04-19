package dev.hld.Notes.exceptions;

public class NoteNotFoundException extends RuntimeException {
    private static final long serivalVersionUID = 2;

    public NoteNotFoundException(String message) {
        super(message);
    }
}
