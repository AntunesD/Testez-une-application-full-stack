import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Teacher } from '../interfaces/teacher.interface';
import { TeacherService } from './teacher.service';

describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;

  const mockTeachers: Teacher[] = [
    { id: 1, lastName: 'Smith', firstName: 'John', createdAt: new Date(), updatedAt: new Date() },
    { id: 2, lastName: 'Doe', firstName: 'Jane', createdAt: new Date(), updatedAt: new Date() },
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService]
    });

    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Vérifie qu'il n'y a pas de requêtes HTTP en attente
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all teachers', () => {
    service.all().subscribe((teachers) => {
      expect(teachers).toEqual(mockTeachers);
    });

    const req = httpMock.expectOne('api/teacher');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeachers); // Simule une réponse avec les données mockées
  });

  it('should fetch teacher detail', () => {
    const mockTeacher: Teacher = mockTeachers[0];
    const id = '1';

    service.detail(id).subscribe((teacher) => {
      expect(teacher).toEqual(mockTeacher);
    });

    const req = httpMock.expectOne(`api/teacher/${id}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeacher); // Simule une réponse avec une donnée spécifique
  });

  it('should handle 404 error on teacher detail', () => {
    const id = '99';

    service.detail(id).subscribe({
      next: () => fail('Expected an error, not data'),
      error: (error) => {
        expect(error.status).toBe(404);
      }
    });

    const req = httpMock.expectOne(`api/teacher/${id}`);
    req.flush('Teacher not found', { status: 404, statusText: 'Not Found' });
  });
});
