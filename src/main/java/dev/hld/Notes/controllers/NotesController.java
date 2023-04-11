package dev.hld.Notes.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hld.Notes.models.Note;
import dev.hld.Notes.models.User;
import dev.hld.Notes.repositories.NotesRepository;
import dev.hld.Notes.repositories.UserRepository;

@CrossOrigin(origins = "*")
@RestController
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/notes")
    public ResponseEntity<List<Note>> getAllNotesByUserId(@PathVariable(value = "userId") String userId) throws RelationNotFoundException {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RelationNotFoundException("Not found User with id = " + userId));
        List<Note> notes = new ArrayList<Note>();
        notes.addAll(user.getNote());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/notes")
    public ResponseEntity<Note> createNote(@PathVariable(value = "userId") String userId, @RequestBody Note noteRequest) throws RelationNotFoundException {
        Note notes = userRepository.findById(userId).map(user -> {
            user.getNote().add(noteRequest);
            return notesRepository.save(noteRequest);
        }).orElseThrow(() -> new RelationNotFoundException("Not found User with id = " + userId));

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

}
