package dev.hld.Notes.repositories;

import org.springframework.data.repository.CrudRepository;

import dev.hld.Notes.models.Notes;

public interface NotesRepository extends CrudRepository<Notes, String>{
    
}
