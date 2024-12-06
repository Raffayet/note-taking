package com.example.notes.seeder;

import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// Class for creating initial notes
@Component
public class NoteSeeder {

    @Autowired
    private NoteRepository noteRepository;

    @PostConstruct
    public void init() {
        deleteSeedData();
        createInitialNotes();
    }

    // Every time we run the app we must first delete the data from the previous runtime
    public void deleteSeedData() {
        noteRepository.deleteAll();
    }

    public void createInitialNotes() {
        noteRepository.saveAll(Arrays.asList(
                new Note("First Note", "This is the content of the first note."),
                new Note("Second Note", "This is the content of the second note."),
                new Note("Third Note", "This is the content of the third note.")
        ));
    }
}
