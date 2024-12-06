import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NoteService } from '../../services/note.service';

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.css'],
})
export class NotesComponent implements OnInit {
  notes: Array<{ id: string; title: string; content: string }> = [];

  constructor(private notesService: NoteService, private router: Router) {}

  ngOnInit(): void {
    this.fetchNotes();
  }

  fetchNotes(): void {
    this.notesService.getNotes().subscribe({
      next: (data) => {
        console.log(data);
        this.notes = data;
      },
      error: (err) => {
        console.error('Failed to fetch notes:', err);
      },
    });
  }

  viewNote(id: string): void {
    this.router.navigate(['/notes', id]);
  }

  editNote(id: string): void {
    this.router.navigate(['/notes/edit', id]);
  }

  deleteNote(id: string): void {
    // this.notesService.deleteNote(id).subscribe({
    //   next: () => {
    //     this.notes = this.notes.filter((note) => note.id !== id);
    //   },
    //   error: (err) => {
    //     console.error('Failed to delete note:', err);
    //   },
    // });
  }
}
