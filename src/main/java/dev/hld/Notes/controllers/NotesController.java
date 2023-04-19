package dev.hld.Notes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.hld.Notes.dto.NoteDto;
import dev.hld.Notes.service.NoteService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class NotesController {
    
    private NoteService noteService;

    @Autowired
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/user/{userId}/note")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NoteDto> createNote(@PathVariable(value = "userId") int userId, @RequestBody NoteDto noteDto) {
        return new ResponseEntity<>(noteService.createNote(userId, noteDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/notes")
    public List<NoteDto> getNoteByUserId(@PathVariable(value = "userId") int userId) {
        return noteService.getNoteByUserid(userId);
    }

    @GetMapping("/user/{userId}/notes/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable(value = "userId") int userId, @PathVariable(value = "id") int noteId) {
        NoteDto noteDto = noteService.getNoteById(userId, noteId);
        return new ResponseEntity<>(noteDto, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/notes/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable(value = "userId") int userId, @PathVariable(value = "id") int reviewId,
                                                  @RequestBody NoteDto noteDto) {
        NoteDto updatedNote = noteService.updateNote(userId, reviewId, noteDto);
        return new ResponseEntity<>(updatedNote, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/notes/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable(value = "userId") int userId, @PathVariable(value = "id") int noteId) {
        noteService.deleteNote(userId, noteId);
        return new ResponseEntity<>("Note deleted successfully", HttpStatus.OK);
    }
}
