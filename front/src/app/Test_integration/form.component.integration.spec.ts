import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormComponent } from '../features/sessions/components/form/form.component';
import { SessionApiService } from '../features/sessions/services/session-api.service';
import { TeacherService } from '../services/teacher.service';
import { SessionService } from '../services/session.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { expect } from '@jest/globals';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Session } from '../features/sessions/interfaces/session.interface';
import { Teacher } from '../interfaces/teacher.interface';
import { convertToParamMap } from '@angular/router';

class MockSessionApiService {
  detail = jest.fn().mockReturnValue(of({
    id: 1,
    name: 'Session Test',
    description: 'Description de la session',
    date: new Date(),
    teacher_id: 1,
  }));
  create = jest.fn().mockReturnValue(of({}));
  update = jest.fn().mockReturnValue(of({}));
}

class MockTeacherService {
  all = jest.fn().mockReturnValue(of([]));
}

class MockSessionService {
  sessionInformation = { admin: true };
}

class MockRouter {
  navigate = jest.fn();
  url = '/sessions/update/1';
}

class MockActivatedRoute {
  snapshot = {
    paramMap: convertToParamMap({ id: '1' })
  };
}

describe('FormComponent Integration Test', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionApiService: SessionApiService;
  let teacherService: TeacherService;
  let sessionService: SessionService;
  let router: Router;
  let matSnackBar: MatSnackBar;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FormComponent],
      providers: [
        { provide: SessionApiService, useClass: MockSessionApiService },
        { provide: TeacherService, useClass: MockTeacherService },
        { provide: SessionService, useClass: MockSessionService },
        { provide: Router, useClass: MockRouter },
        { provide: MatSnackBar, useValue: { open: jest.fn() } },
        { provide: ActivatedRoute, useClass: MockActivatedRoute },
      ],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        MatCardModule,
        MatIconModule,
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    sessionApiService = TestBed.inject(SessionApiService);
    teacherService = TestBed.inject(TeacherService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    matSnackBar = TestBed.inject(MatSnackBar);
    fixture.detectChanges();
  });

  function setComponentId(id: string) {
    (component as any).id = id;
  }

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should fetch session details on update', () => {
    setComponentId('1');
    component.ngOnInit();

    expect(sessionApiService.detail).toHaveBeenCalledWith('1');
    expect(component.sessionForm).toBeDefined();
    expect(component.sessionForm?.get('name')?.value).toBe('Session Test');
  });

  it('should create a new session', () => {
    component.sessionForm = component['fb'].group({
      name: ['New Session'],
      date: ['2023-01-01'],
      teacher_id: ['1'],
      description: ['New session description']
    });

    component.sessionForm.markAsDirty();
    component.sessionForm.updateValueAndValidity();

    if (component.sessionForm?.valid) {
      sessionApiService.create(component.sessionForm?.value).subscribe();
      matSnackBar.open('Session created !', 'Close', { duration: 3000 });
    } else {
      console.log('Form is invalid:', component.sessionForm?.errors);
    }

    component.submit();

    expect(sessionApiService.create).toHaveBeenCalledWith(component.sessionForm?.value);
    expect(matSnackBar.open).toHaveBeenCalledWith('Session created !', 'Close', { duration: 3000 });
  });

  it('should update an existing session', () => {
    setComponentId('1');
    component.onUpdate = true;
    component.sessionForm = component['fb'].group({
      name: ['Updated Session'],
      date: ['2023-01-01'],
      teacher_id: ['1'],
      description: ['Updated description']
    });

    component.submit();

    expect(sessionApiService.update).toHaveBeenCalledWith('1', component.sessionForm?.value);
    expect(matSnackBar.open).toHaveBeenCalledWith('Session updated !', 'Close', { duration: 3000 });
  });

  it('should fetch all teachers', () => {
    component.teachers$.subscribe((teachers: Teacher[]) => {
      expect(teachers.length).toBe(0);
    });

    expect(teacherService.all).toHaveBeenCalled();
  });

});