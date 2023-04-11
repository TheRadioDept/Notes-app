package dev.hld.Notes.models;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Notes")
public class Note {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String noteId;

    private String noteBody;

    public String getNoteId() {
        return this.noteId;
    }

    public String getNoteBody() {
        return this.noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public Note() {
    }

    public Note(String noteId, String noteBody) {
        this.noteId = noteId;
        this.noteBody = noteBody;
    }

    public Note(String noteBody) {
        this(UUID.randomUUID().toString(), noteBody);
    }
}
