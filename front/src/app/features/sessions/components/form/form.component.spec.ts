import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';

import { FormComponent } from './form.component';
import { Session } from '../../interfaces/session.interface';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  };

  const mockSessionServiceNotAdmin = {
    sessionInformation: {
      admin: false,
      token: '',
      type: '',
      id: 0,
      username: '',
    },
    isLogged: false,
    isLoggedSubject: of(false),
    $isLogged: jest.fn().mockReturnValue(of(false)),
    logIn: jest.fn(),
    logOut: jest.fn(),
    next: jest.fn(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: MatSnackBar, useValue: { open: jest.fn() } },
        SessionApiService
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form correctly for update', () => {
    // Simuler l'URL pour l'édition
    jest.spyOn(component['router'], 'url', 'get').mockReturnValue('/sessions/update/1');
    component.ngOnInit();

    expect(component.onUpdate).toBe(true);
    expect(component.sessionForm).toBeDefined();
    expect(component.sessionForm?.get('name')?.value).toBe(''); // Vérifiez la valeur par défaut
  });


  it('should submit the form and create a session', () => {
    component.onUpdate = false;
    component.sessionForm = component['fb'].group({
      name: ['Test Session'],
      date: ['2023-01-01'],
      teacher_id: ['1'],
      description: ['Description of the session']
    });

    const mockSession: Session = {
      id: 1,
      name: 'Test Session',
      description: 'Description of the session',
      date: new Date('2023-01-01'),
      teacher_id: 1,
      users: [],
      createdAt: new Date(),
      updatedAt: new Date(),
    };

    const createSpy = jest.spyOn(component['sessionApiService'], 'create').mockReturnValue(of(mockSession));
    component.submit();

    expect(createSpy).toHaveBeenCalledWith(component.sessionForm?.value);
    expect(component['matSnackBar'].open).toHaveBeenCalledWith('Session created !', 'Close', { duration: 3000 });
  });

  it('should submit the form and update a session', () => {
    component['onUpdate'] = true;
    component['id'] = '1';
    component.sessionForm = component['fb'].group({
      name: ['Updated Session'],
      date: ['2023-01-01'],
      teacher_id: ['1'],
      description: ['Updated description']
    });

    const mockSession: Session = {
      name: 'Updated Session',
      date: new Date('2023-01-01'),
      teacher_id: 1,
      description: 'Updated description',
      users: [],
    };

    const updateSpy = jest.spyOn(component['sessionApiService'], 'update').mockReturnValue(of(mockSession));
    component.submit();

    expect(updateSpy).toHaveBeenCalledWith(component['id'], component.sessionForm?.value);
    expect(component['matSnackBar'].open).toHaveBeenCalledWith('Session updated !', 'Close', { duration: 3000 });
  });
});
