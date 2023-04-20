package dev.hld.Notes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.hld.Notes.dto.NoteDto;
import dev.hld.Notes.service.NoteService;
import dev.hld.Notes.exceptions.NoteNotFoundException;
import dev.hld.Notes.exceptions.UserNotFoundException;
import dev.hld.Notes.models.Note;
import dev.hld.Notes.models.User;
import dev.hld.Notes.repositories.NotesRepository;
import dev.hld.Notes.repositories.UserRepository;

@Service
public class NoteServiceImpl implements NoteService {
    private NotesRepository notesRepository;
    private UserRepository userRepository;

    public NoteServiceImpl(NotesRepository notesRepository, UserRepository userRepository) {
        this.notesRepository = notesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NoteDto createNote(int userId, NoteDto noteDto) {
        Note note = mapToEntity(noteDto);
    
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with associated id not found"));
    
        note.setUser(user);
    
        Note newNote = notesRepository.save(note);
    
        return mapToDto(newNote);
      }

    @Override
    public List<NoteDto> getNoteByUserid(int userId) {
        List<Note> notes = notesRepository.findByUserId(userId);

        return notes.stream().map(note -> mapToDto(note)).collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(int userId, int noteId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User could not be found"));

        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note could not be found"));

        if (note.getUser().getId() != user.getId()) {
            throw new NoteNotFoundException("This note does not belong to a user");
        }

        return mapToDto(note);
    }

    @Override
    public NoteDto updateNote(int userId, int noteId, NoteDto noteDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User could not be found"));

        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note could not be found"));

        if (note.getUser().getId() != user.getId()) {
            throw new NoteNotFoundException("This note does not belong to a user");
        }

        note.setTopic(noteDto.getTopic());
        note.setNoteBody(noteDto.getNoteBody());

        Note updatedNote = notesRepository.save(note);

        return mapToDto(updatedNote);
    }

    @Override
    public void deleteNote(int userId, int noteId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User could not be found"));

        Note note = notesRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note could not be found"));

        if (note.getUser().getId() != user.getId()) {
            throw new NoteNotFoundException("This note does not belong to a user");
        }

        notesRepository.delete(note);
    }

    private NoteDto mapToDto(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setNoteId(note.getId());
        noteDto.setTopic(note.getTopic());
        noteDto.setNoteBody(note.getNoteBody());
        return noteDto;
    }

    private Note mapToEntity(NoteDto noteDto) {
        Note note = new Note();
        note.setId(noteDto.getNoteId());
        note.setTopic(noteDto.getTopic());
        note.setNoteBody(noteDto.getNoteBody());
        return note;
    }
}
