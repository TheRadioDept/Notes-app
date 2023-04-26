package dev.hld.Notes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hld.Notes.models.Note;

public interface NotesRepository extends JpaRepository<Note, Integer> {
    List<Note> findByUserId(int userId);
}
