import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ToastrService } from 'ngx-toastr';
import { NoteService } from '../../services/note.service';

@Component({
  selector: 'app-note-details',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
  ],
  templateUrl: './note-details.component.html',
  styleUrl: './note-details.component.css',
})
export class NoteDetailsComponent {
  noteForm: FormGroup;
  createDialog: boolean;

  constructor(
    private toastrService: ToastrService,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<NoteDetailsComponent>,
    private noteService: NoteService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.noteForm = this.fb.group({
      title: [data?.title || '', Validators.required],
      content: [data?.content || '', Validators.required],
    });

    this.createDialog = !this.data || (!this.data.title && !this.data.content);
  }

  onSubmit(): void {
    if (this.noteForm.valid) {
      const noteData = this.noteForm.value;

      if (this.createDialog) {
        // POST request if the user is creating new note
        this.noteService.createNote(noteData).subscribe({
          next: (response) => {
            this.toastrService.success('Note created successfully!');
            this.dialogRef.close(response);
          },
          error: (error: HttpErrorResponse) => {
            this.toastrService.error('Failed to create note!');
          },
        });
      } else {
        // PUT request if the user is updating the note
        this.noteService.updateNote(this.data.id, noteData).subscribe({
          next: (response) => {
            this.toastrService.success('Note updated successfully!');
            this.dialogRef.close(response);
          },
          error: (error: HttpErrorResponse) => {
            this.toastrService.error('Failed to update note!');
          },
        });
      }
    }
  }
}
