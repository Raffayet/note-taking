package com.example.notes;

import com.example.notes.controller.NoteController;
import com.example.notes.exception.NotFoundException;
import com.example.notes.model.Note;
import com.example.notes.repository.NoteRepository;
import com.example.notes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class NotesApplicationTests {

	@Mock
	private NoteService noteService;

	@InjectMocks
	private NoteController noteController;

	private Note note;
    @Autowired
    private NoteRepository noteRepository;

	@BeforeEach
	void setUp() {
		note = new Note();
		note.setId("actualId");
		note.setTitle("Test Note 1");
		note.setContent("This is a test note 1.");
	}

	@Test
	void testCreateNote_Success() {
		when(noteService.createNote(any(Note.class))).thenReturn(note);

		// It returns this.note regardless of the mimicking new note value here
		Note newNote = new Note();
		newNote.setTitle("New Note");
		newNote.setContent("New Content");

		ResponseEntity<Note> response = noteController.createNote(newNote);

		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(note.getTitle(), response.getBody().getTitle());
		assertEquals(note.getContent(), response.getBody().getContent());
	}

	@Test
	void testGetAllNotes_Success() {
		int expectedTotalElements = 1;
		int page = 0;
		String search = "";
		List<Note> notes = List.of(note);
		Page<Note> notePage = new PageImpl<>(notes);
		when(noteService.getNotes(search, page, 10)).thenReturn(notePage);

		ResponseEntity<Page<Note>> response = noteController.getNotes(search, page, 10);

		assertNotNull(response.getBody());
		assertEquals(expectedTotalElements, response.getBody().getTotalElements());
		assertEquals(note.getTitle(), response.getBody().getContent().get(0).getTitle());
	}

	@Test
	void testGetNoteById_Success() {
		when(noteService.getNoteById("actualId")).thenReturn(note);

		ResponseEntity<Note> response = noteController.getNoteById("actualId");

		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(note.getTitle(), response.getBody().getTitle());
		assertEquals(note.getContent(), response.getBody().getContent());
	}

	@Test
	void testGetNoteById_NotFound() {
		// When having some id that is different from the valid one - we should have exception
		when(noteService.getNoteById("someOtherId")).thenThrow(new NotFoundException("Note not found"));

		NotFoundException exception = assertThrows(NotFoundException.class, () -> {
			noteController.getNoteById("someOtherId");
		});

		assertEquals("Note not found", exception.getMessage());
	}

	@Test
	void testUpdateNote_Success() {
		Note updatedNote = new Note();
		updatedNote.setTitle("Updated Title");
		updatedNote.setContent("Updated Content");
		String id = "actualId";

		doNothing().when(noteService).updateNote(id, updatedNote);

		ResponseEntity response = noteController.updateNote(id, updatedNote);

		assertNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(noteService, times(1)).updateNote(id, updatedNote);
	}

	@Test
	void testUpdateNote_NotFound() {
		Note updatedNote = new Note();
		updatedNote.setTitle("Updated Title");
		updatedNote.setContent("Updated Content");
		String id = "someOtherId";

		doThrow(new NotFoundException("Note not found")).when(noteService).updateNote(id, updatedNote);

		NotFoundException exception = assertThrows(NotFoundException.class, () -> {
			noteController.updateNote(id, updatedNote);
		});

		assertEquals("Note not found", exception.getMessage());
		verify(noteService, times(1)).updateNote(id, updatedNote);
	}

	@Test
	void testDeleteNoteById_Success() {
		String existingId = "actualId";
		doNothing().when(noteService).deleteNoteById(existingId);

		ResponseEntity<Void> response = noteController.deleteNoteById(existingId);

		assertNull(response.getBody());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(noteService, times(1)).deleteNoteById(existingId);
	}

	@Test
	void testDeleteNoteById_NotFound() {
		// Arrange
		String nonExistingId = "someOtherId";
		doThrow(new NotFoundException("Note not found")).when(noteService).deleteNoteById(nonExistingId);

		// Act & Assert
		NotFoundException exception = assertThrows(NotFoundException.class, () -> {
			noteController.deleteNoteById(nonExistingId);
		});

		assertEquals("Note not found", exception.getMessage());
		verify(noteService, times(1)).deleteNoteById(nonExistingId);
	}
}
