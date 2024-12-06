import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { NotesComponent } from './components/notes/notes.component';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'notes', component: NotesComponent },
];
