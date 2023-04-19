package dev.hld.Notes.service;

import java.util.List;

import dev.hld.Notes.dto.NoteDto;

public interface NoteService {
    NoteDto createNote(int userId, NoteDto noteDto);

    List<NoteDto> getNoteByUserid(int noteId);

    NoteDto getNoteById(int userId, int noteId);

    NoteDto updateNote(int userId, int noteId, NoteDto noteDto);

    void deleteNote(int userId, int noteId);

}
