import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialog,
  MatDialogActions,
  MatDialogModule,
} from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { Router } from '@angular/router';
import { Note } from '../../model/note';
import { PageRequest } from '../../model/pageRequest';
import { NoteService } from '../../services/note.service';
import { NoteDetailsComponent } from '../note-details/note-details.component';

@Component({
  selector: 'app-notes',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatIconModule,
    MatDialogModule,
    MatButtonModule,
    MatDialogActions,
  ],
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.css'],
})
export class NotesComponent implements OnInit {
  notes: Note[] = [];
  displayedColumns: string[] = ['title', 'content', 'actions'];

  constructor(
    private notesService: NoteService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.fetchNotes({ page: 0, size: 10 });
  }

  fetchNotes(pageRequest: PageRequest): void {
    this.notesService.getNotes(pageRequest).subscribe({
      next: (data) => {
        console.log(data);
        this.notes = data['content'] || [];
      },
      error: (err) => {
        console.error('Failed to fetch notes:', err);
        this.notes = [];
      },
    });
  }

  openNoteDetailsDialog(row?: any): void {
    this.dialog.open(NoteDetailsComponent, {
      width: '1000px',
      height: '500px',
      data: row,
    });
  }

  viewNote(id: string): void {
    this.router.navigate(['/notes', id]);
  }

  editNote(id: string): void {
    this.router.navigate(['/notes/edit', id]);
  }

  deleteNote(note: Note): void {
    const noteId = note.id;
    console.log(note.id);
    this.notesService.deleteNote(noteId).subscribe({
      next: () => {
        console.log('what');
        this.notes = this.notes.filter((note) => note.id !== noteId);
      },
      error: (err) => {
        console.error('Failed to delete note:', err);
      },
    });
  }
}
