package dev.hld.Notes.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hld.Notes.models.Notes;
import dev.hld.Notes.repositories.NotesRepository;

@RestController
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @GetMapping("/notes")
    Iterable<Notes> getNotes() {
        return notesRepository.findAll();
    }

    @GetMapping("/notes/{id}")
    Optional<Notes> getNoteById(@PathVariable String noteId) {
        return notesRepository.findById(noteId);
    }

    @PostMapping("/notes/add")
    Notes addNote(@RequestParam String note_body) {
        Notes note = new Notes(note_body);
        return notesRepository.save(note);
    }

}
