package dev.hld.Notes.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.hld.Notes.models.Note;

public interface NotesRepository extends CrudRepository<Note, String>{
    
}
