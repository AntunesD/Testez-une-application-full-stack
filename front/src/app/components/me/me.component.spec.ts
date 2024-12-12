import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, fakeAsync, flush, TestBed } from '@angular/core/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { expect } from '@jest/globals';
import { of } from 'rxjs';
import { User } from '../../interfaces/user.interface';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';

import { NgZone } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MeComponent } from './me.component';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;
  let router: Router;
  let sessionService: SessionService;

  const mockUser: User = {
    id: 1,
    email: 'test@example.com',
    firstName: 'John',
    lastName: 'Doe',
    admin: true,
    password: 'password123',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
    logOut: jest.fn(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [HttpClientTestingModule, MatSnackBarModule, NoopAnimationsModule, MatCardModule, MatIconModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        UserService,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load user data on init', () => {
    jest.spyOn(userService, 'getById').mockReturnValue(of(mockUser));

    component.ngOnInit();

    expect(userService.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(mockUser);
  });

  it('should navigate back on back()', () => {
    const spy = jest.spyOn(window.history, 'back');
    component.back();
    expect(spy).toHaveBeenCalled();
  });

  it('should delete the user and log out', fakeAsync(() => {
    jest.spyOn(userService, 'delete').mockReturnValue(of({}));
    const logOutSpy = jest.spyOn(sessionService, 'logOut');
    const routerSpy = jest.spyOn(router, 'navigate');
    const snackBarSpy = jest.spyOn(component['matSnackBar'], 'open');

    // Appeler la méthode delete dans NgZone
    const ngZone = TestBed.inject(NgZone);
    ngZone.run(() => {
      component.delete();
    });

    // Simuler l'écoulement du temps
    flush();

    // Vérifier que les méthodes ont été appelées
    expect(userService.delete).toHaveBeenCalledWith('1');
    expect(logOutSpy).toHaveBeenCalled();
    expect(snackBarSpy).toHaveBeenCalledWith(
      'Your account has been deleted !',
      'Close',
      { duration: 3000 }
    );
    expect(routerSpy).toHaveBeenCalledWith(['/']);
  }));


});
