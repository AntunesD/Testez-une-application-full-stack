import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { Session } from '../interfaces/session.interface';
import { SessionApiService } from './session-api.service';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService],
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve all sessions', () => {
    const mockSessions: Session[] = [
      { id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [1, 2] },
      { id: 2, name: 'Session 2', description: 'Description 2', date: new Date(), teacher_id: 2, users: [2, 3] },
    ];

    service.all().subscribe(sessions => {
      expect(sessions.length).toBe(2);
      expect(sessions).toEqual(mockSessions);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(mockSessions);
  });

  it('should create a new session', () => {
    const newSession: Session = { name: 'New Session', description: 'Description', date: new Date(), teacher_id: 1, users: [1, 2] };
    const createdSession: Session = { id: 3, ...newSession };

    service.create(newSession).subscribe(session => {
      expect(session).toEqual(createdSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newSession);
    req.flush(createdSession);
  });

  it('should delete a session', () => {
    const sessionId = '1';

    service.delete(sessionId).subscribe(response => {
      expect(response).toBeNull(); // Assuming the response is empty or null
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should update a session', () => {
    const updatedSession: Session = { id: 1, name: 'Updated Session', description: 'Updated Description', date: new Date(), teacher_id: 1, users: [1, 2] };

    service.update('1', updatedSession).subscribe(session => {
      expect(session).toEqual(updatedSession);
    });

    const req = httpMock.expectOne('api/session/1');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedSession);
    req.flush(updatedSession);
  });

  it('should participate in a session', () => {
    const sessionId = '1';
    const userId = '2';

    service.participate(sessionId, userId).subscribe(response => {
      expect(response).toBeUndefined(); // No content expected in response
    });

    const req = httpMock.expectOne(`api/session/1/participate/2`);
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should unparticipate from a session', () => {
    const sessionId = '1';
    const userId = '2';

    // Appel de la méthode unParticipate
    service.unParticipate(sessionId, userId).subscribe(response => {
      expect(response).toBeUndefined(); // Aucune donnée dans la réponse attendue
    });

    // Vérification de la requête HTTP
    const req = httpMock.expectOne(`api/session/1/participate/2`);
    expect(req.request.method).toBe('DELETE');

    // Envoi de la réponse simulée (réponse vide)
    req.flush(null);
  });

});