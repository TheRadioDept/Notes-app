package dev.hld.Notes.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.RelationNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.hld.Notes.models.Note;
import dev.hld.Notes.models.User;
import dev.hld.Notes.repositories.NotesRepository;
import dev.hld.Notes.repositories.UserRepository;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "*")
@RestController
public class NotesController {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{userId}/notes")
    public ResponseEntity<List<Note>> getAllNotesByUserId(@PathVariable(value = "userId") String userId)
            throws RelationNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RelationNotFoundException("Not found User with id = " + userId));
        List<Note> notes = new ArrayList<Note>();
        notes.addAll(user.getNotes());
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/notes")
    Iterable<Note> getNotes() {
        return notesRepository.findAll();
    }

    @GetMapping("/users/{userId}/notes/{noteId}")
    public ResponseEntity<Note> getSingleNote(@PathVariable(value = "userId") String userId,
            @PathVariable(value = "noteId") String noteId) throws RelationNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new RelationNotFoundException("Not found User with id = " + userId));
        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new RelationNotFoundException("Not found User with id = " + noteId));
        ;
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/notes")
    public ResponseEntity<Note> createNote(@PathVariable(value = "userId") String userId, @RequestBody Note noteRequest)
            throws RelationNotFoundException {
        Note notes = userRepository.findById(userId).map(user -> {
            user.getNotes().add(noteRequest);
            return notesRepository.save(noteRequest);
        }).orElseThrow(() -> new RelationNotFoundException("Not found User with id = " + userId));

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}/notes/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") String id, @PathVariable("userId") String userId,
            @RequestBody Note commentRequest) throws RelationNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new RelationNotFoundException("Not found User with id = " + userId));
        Note comment = notesRepository.findById(id)
                .orElseThrow(() -> new RelationNotFoundException("CommentId " + id + "not found"));

        comment.setNoteBody(commentRequest.getNoteBody());

        return new ResponseEntity<>(notesRepository.save(comment), HttpStatus.OK);
    }

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable String id) {
        notesRepository.deleteById(id);
    }
}
