import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { SessionService } from './session.service';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should have initial isLogged as false and sessionInformation as undefined', () => {
    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should update isLogged to true and set sessionInformation on logIn', () => {
    const user: SessionInformation = {
      token: 'token123',
      type: 'bearer',
      id: 1,
      username: 'johndoe',
      firstName: 'John',
      lastName: 'Doe',
      admin: false,
    };

    service.logIn(user);

    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(user);
  });

  it('should update isLogged to false and reset sessionInformation on logOut', () => {
    const user: SessionInformation = {
      token: 'token123',
      type: 'bearer',
      id: 1,
      username: 'johndoe',
      firstName: 'John',
      lastName: 'Doe',
      admin: false,
    };

    service.logIn(user);
    service.logOut();

    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit correct value for $isLogged on login and logout', (done) => {
    const user: SessionInformation = {
      token: 'token123',
      type: 'bearer',
      id: 1,
      username: 'johndoe',
      firstName: 'John',
      lastName: 'Doe',
      admin: false,
    };

    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(true);
    });

    service.logIn(user); // should trigger the observable to emit true

    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(false);
      done();
    });

    service.logOut(); // should trigger the observable to emit false
  });
});
