import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';
import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [AuthService, SessionService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have invalid form initially', () => {
    expect(component.form.invalid).toBe(true);
  });

  it('should validate email and password fields', () => {
    const email = component.form.get('email');
    const password = component.form.get('password');

    email?.setValue('invalid-email');
    password?.setValue('123');
    expect(component.form.invalid).toBe(true); // invalid email

    email?.setValue('test@example.com');
    password?.setValue('000001');
    expect(component.form.invalid).toBe(true); // invalid password 

    password?.setValue('3');
    expect(component.form.valid).toBe(true); // form should be valid now
  });

  it('should call authService.login and sessionService.logIn on valid submit', () => {
    const loginRequest: LoginRequest = { email: 'test@example.com', password: '123456' };
    const sessionInfo: SessionInformation = {
      token: 'token123',
      type: 'bearer',
      id: 1,
      username: 'testuser',
      firstName: 'John',
      lastName: 'Doe',
      admin: false,
    };

    jest.spyOn(authService, 'login').mockReturnValue(of(sessionInfo));
    jest.spyOn(sessionService, 'logIn');
    const navigateSpy = jest.spyOn(router, 'navigate');

    component.form.setValue({ email: 'test@example.com', password: '123456' });
    component.submit();

    expect(authService.login).toHaveBeenCalledWith(loginRequest);
    expect(sessionService.logIn).toHaveBeenCalledWith(sessionInfo);
    expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
  });

  it('should set onError to true on login failure', () => {
    const loginRequest: LoginRequest = { email: 'test@example.com', password: '123456' };

    jest.spyOn(authService, 'login').mockReturnValue(throwError('Login failed'));

    component.form.setValue({ email: 'test@example.com', password: '123456' });
    component.submit();

    expect(component.onError).toBe(true);
  });
});
