package com.example.notes.service;

import com.example.notes.exception.NotFoundException;
import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public void updateNote(String id, Note updatedNote) {
        Optional<Note> note = noteRepository.findById(id);
        // Only if we find the note by id, then we can continute with updating
        if(note.isPresent()) {
            Note presentNote = note.get();
            presentNote.setContent(updatedNote.getContent());
            presentNote.setTitle(updatedNote.getTitle());
            presentNote.setUpdatedAt(Instant.now());
            noteRepository.save(presentNote);
        }
    }

    public Page<Note> getNotes(String search, int page, int size) {
        // Implementing pagination here
        Pageable pageable = PageRequest.of(page, size);
        // If user didn't search anything return the all notes immediately
        if (search == null || search.isEmpty()) {
            return noteRepository.findAll(pageable);
        }
        return noteRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(search, search, pageable);
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Note not found with ID: " + id));
    }

    public void deleteNoteById(String id) {
        if (!noteRepository.existsById(id)) {
            throw new NotFoundException("Note with ID " + id + " does not exist");
        }
        noteRepository.deleteById(id);
    }

}
