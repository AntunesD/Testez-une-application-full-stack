import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Router } from '@angular/router';
import { expect } from '@jest/globals';
import { of, throwError } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { RegisterComponent } from './register.component';

//TODO: La validation des champs est incorrecte (voir les commentaires dans les tests)

class MockAuthService {
  register() {
    return of(void 0); // Simule une réponse réussie
  }
}

class MockRouter {
  navigate = jest.fn(); // Mock pour la navigation
}

describe('RegisterComponent', () => {
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
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have an invalid form initially', () => {
    expect(component.form.invalid).toBe(true);
  });

  it('should validate email field', () => {
    const email = component.form.get('email');
    email?.setValue('');
    expect(email?.invalid).toBe(true); // Required

    email?.setValue('invalid-email');
    expect(email?.invalid).toBe(true); // Invalid format

    email?.setValue('test@example.com');
    expect(email?.valid).toBe(true); // Valid email
  });

  it('should validate firstName field', () => {
    const firstName = component.form.get('firstName');
    firstName?.setValue('');
    expect(firstName?.invalid).toBe(true); // Required

    //si je mets pleins de chiffres ce ne passe pas la validation
    firstName?.setValue('00000000000000000001');
    expect(firstName?.invalid).toBe(true);
    //mais si je mets un seule chiffre superieur à 3 ça passe
    firstName?.setValue('3');
    expect(firstName?.valid).toBe(true);
    //si je mets un seul nombre superieur à 20 ça ne passe pas
    firstName?.setValue('21');
    expect(firstName?.invalid).toBe(true);
    //mais une longue phrase ça passe
    firstName?.setValue('un super long prénom qui ne devrait pas passer la validation');
    expect(firstName?.valid).toBe(true); //C'est pas normal

    firstName?.setValue('John');
    expect(firstName?.valid).toBe(true); // Valid
  });

  it('should validate lastName field', () => {
    const lastName = component.form.get('lastName');
    lastName?.setValue('');
    expect(lastName?.invalid).toBe(true); // Required

    lastName?.setValue('0');
    expect(lastName?.invalid).toBe(true); // Too short

    lastName?.setValue('999');
    expect(lastName?.invalid).toBe(true); // Too long

    lastName?.setValue('Doe');
    expect(lastName?.valid).toBe(true); // Valid
  });

  it('should validate password field', () => {
    const password = component.form.get('password');
    password?.setValue('');
    expect(password?.invalid).toBe(true); // Required

    password?.setValue('0000000001');
    expect(password?.invalid).toBe(true); // pas normal

    password?.setValue('99');
    expect(password?.invalid).toBe(true); // pas normal

    password?.setValue('password123');
    expect(password?.valid).toBe(true); // Valid
  });

  it('should call authService.register and navigate on valid submit', () => {
    const registerSpy = jest.spyOn(authService, 'register').mockReturnValue(of(void 0));
    const navigateSpy = jest.spyOn(router, 'navigate');

    component.form.setValue({
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123'
    });

    component.submit();

    expect(registerSpy).toHaveBeenCalledWith({
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      password: 'password123',
    });
    expect(navigateSpy).toHaveBeenCalledWith(['/login']);
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
