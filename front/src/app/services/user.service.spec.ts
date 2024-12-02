import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { User } from '../interfaces/user.interface';
import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  const mockUser: User = {
    id: 1,
    email: 'test@example.com',
    firstName: 'John',
    lastName: 'Doe',
    admin: false,
    password: 'password123',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService],
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Vérifie qu'aucune requête HTTP inattendue n'a été effectuée
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve a user by ID', () => {
    const userId = '1';

    service.getById(userId).subscribe((user) => {
      expect(user).toEqual(mockUser);
    });

    // Simuler une requête HTTP GET
    const req = httpMock.expectOne(`api/user/${userId}`);
    expect(req.request.method).toBe('GET');

    // Simuler une réponse HTTP
    req.flush(mockUser);
  });

  it('should delete a user by ID', () => {
    const userId = '1';

    service.delete(userId).subscribe((response) => {
      expect(response).toBeTruthy(); // Vérifie que l'API répond correctement
    });

    // Simuler une requête HTTP DELETE
    const req = httpMock.expectOne(`api/user/${userId}`);
    expect(req.request.method).toBe('DELETE');

    // Simuler une réponse HTTP vide (cas courant pour une suppression réussie)
    req.flush({});
  });
});
