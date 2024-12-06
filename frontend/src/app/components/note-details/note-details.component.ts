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
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<NoteDetailsComponent>,
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
      this.dialogRef.close(this.noteForm.value);
    }
  }
}
