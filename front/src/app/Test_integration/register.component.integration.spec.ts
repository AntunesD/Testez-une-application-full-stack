import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from '../features/auth/components/register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../features/auth/services/auth.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { expect } from '@jest/globals';

class MockAuthService {
  register() {
    return of(void 0); // Simule une réponse réussie
  }
}

class MockRouter {
  navigate = jest.fn(); // Mock pour la navigation
}

describe('RegisterComponent Integration Test', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: AuthService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useClass: MockAuthService },
        { provide: Router, useClass: MockRouter },
      ],
      imports: [ReactiveFormsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should navigate to login on successful registration', () => {
    component.form.setValue({
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123'
    });
    component.submit();
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should set onError to true on registration failure', () => {
    jest.spyOn(authService, 'register').mockReturnValue(throwError('Registration failed'));
    component.form.setValue({
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123'
    });
    component.submit();
    expect(component.onError).toBe(true);
  });
}); 