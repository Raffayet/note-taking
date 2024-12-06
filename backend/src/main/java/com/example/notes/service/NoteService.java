package com.example.notes.service;

import com.example.notes.exception.NotFoundException;
import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Page<Note> getAllNotes(Pageable paging) {
        Page<Note> notes = noteRepository.findAll(paging);
        return notes;
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Note not found with ID: " + id));
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }
}
