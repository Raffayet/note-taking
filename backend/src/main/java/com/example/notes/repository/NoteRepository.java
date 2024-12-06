package com.example.notes.repository;

import com.example.notes.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Page<Note> findAll(Pageable pageable);

    // User can search by title and content - implementing some 'soft search' here
    Page<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
        String title,
        String content,
        Pageable pageable
    );
}
