package com.example.notes.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "notes")
@Getter
@Setter
public class Note {
    @Id
    private ObjectId id;

    private String title;

    private String content;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    // Constructor used when creating initial notes during application startup
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
