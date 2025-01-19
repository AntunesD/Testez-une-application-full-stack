import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from '../features/auth/components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../features/auth/services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { expect } from '@jest/globals';

class MockAuthService {
  login() {
    return of({}); // Simule une réponse réussie
  }
}

class MockRouter {
  navigate = jest.fn(); // Mock pour la navigation
}

describe('LoginComponent Integration Test', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: AuthService, useClass: MockAuthService },
        { provide: Router, useClass: MockRouter },
      ],
      imports: [ReactiveFormsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should navigate to sessions on successful login', () => {
    component.form.setValue({ email: 'test@example.com', password: 'password123' });
    component.submit();
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should set onError to true on login failure', () => {
    jest.spyOn(authService, 'login').mockReturnValue(throwError('Login failed'));
    component.form.setValue({ email: 'test@example.com', password: 'password123' });
    component.submit();
    expect(component.onError).toBe(true);
  });
}); 