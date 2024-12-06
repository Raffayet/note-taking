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
                new Note("Meeting with John", "We need to discuss next phases of the product development. Testing is priority."),
                new Note("Dentist appointment", "Surgeon needs to fix couple of teeth."),
                new Note("Date with Mariah", "Mariah invited me to the restaurant near my location. I'm so excited to meet her!")
        ));
    }
}
