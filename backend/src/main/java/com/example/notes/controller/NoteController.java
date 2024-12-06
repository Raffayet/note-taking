package com.example.notes.controller;

import com.example.notes.model.Note;
import com.example.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    NoteService noteService;

    @GetMapping
    public Page<Note> getAllNotes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        // Implementing pagination here
        Pageable paging = PageRequest.of(page, size);
        return noteService.getAllNotes(paging);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable String id) {
        // Exceptions are automatically handled by exception/GlobalExceptionHandler
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return ResponseEntity.ok(noteService.createNote(note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable String id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.noContent().build();
    }
}
