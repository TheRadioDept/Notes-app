package dev.hld.Notes.models;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "Notes")
@Data public class Note {

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.AUTO)
    private String noteId;

    private String noteBody;

    public Note(String noteId, String noteBody) {
        this.noteId = noteId;
        this.noteBody = noteBody;
    }

    public Note(String noteBody) {
        this(UUID.randomUUID().toString(), noteBody);
    }
}
