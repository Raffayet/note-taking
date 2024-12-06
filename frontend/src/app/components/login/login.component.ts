import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatFormField,
    MatInputModule,
    MatFormFieldModule,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  submitted = false;
  success = false;
  res = '';
  loggedIn: boolean;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private toastService: ToastrService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  get formFields() {
    return this.loginForm.controls;
  }

  onSubmit() {
    localStorage.clear();
    this.submitted = true;

    const { email, password } = this.loginForm.value;

    this.loginService.logIn(email, password, this.success).subscribe({
      next: (res) => {
        console.log('success');
        this.success = true;
        this.router.navigate(['/notes']);
      },
      error: (err: string) => {
        this.toastService.error('Wrong credentials');
        console.log('failure');
        if (err === 'OK') {
          console.log('Credentials are poorly formatted!');
        }
        this.success = false;
      },
    });
  }
}
