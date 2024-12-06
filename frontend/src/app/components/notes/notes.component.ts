import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {
  MatDialog,
  MatDialogActions,
  MatDialogModule,
} from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { Router } from '@angular/router';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
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
    MatInputModule,
    MatPaginatorModule,
  ],
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.css'],
})
export class NotesComponent implements OnInit {
  notes: Note[] = [];
  displayedColumns: string[] = ['title', 'content', 'actions'];
  searchQuery: string = '';
  searchSubject: Subject<string> = new Subject<string>();

  // Pagination properties
  totalLength = 0;
  pageSize = 10;
  currentPage = 0;

  constructor(
    private notesService: NoteService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.searchSubject
      // setting some debounce to avoid calling too many api calls
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe((searchQuery) => {
        this.fetchNotes({ page: 0, size: 10 }, searchQuery);
      });
    this.fetchNotes({ page: 0, size: 10 });
  }

  fetchNotes(pageRequest: PageRequest, search?: string): void {
    this.notesService.getNotes(pageRequest, search).subscribe({
      next: (data) => {
        this.notes = data['content'] || [];
        this.totalLength = data['totalElements'] || 0; // Update total count for paginator
      },
      error: (err) => {
        console.error('Failed to fetch notes:', err);
        this.notes = [];
        this.totalLength = 0; // Reset total count on error
      },
    });
  }

  // Triggers this every time user searches something
  onSearchInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchValue = input.value;
    this.searchSubject.next(searchValue);
  }

  openNoteDetailsDialog(row?: any): void {
    this.dialog
      .open(NoteDetailsComponent, {
        width: '700px',
        height: '600px',
        data: row,
      })
      .afterClosed()
      .subscribe(() => {
        this.fetchNotes({ page: 0, size: 10 });
      });
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.fetchNotes({ page: this.currentPage, size: this.pageSize });
  }

  deleteNote(note: Note): void {
    const noteId = note.id;
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
