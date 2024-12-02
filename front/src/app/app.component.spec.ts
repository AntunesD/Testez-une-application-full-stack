import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, flush } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of } from 'rxjs';

import { Router } from '@angular/router';
import { AppComponent } from './app.component';
import { SessionService } from './services/session.service';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let sessionService: SessionService;
  let router: Router;

  const mockSessionService = {
    logOut: jest.fn(),
    $isLogged: jest.fn().mockReturnValue(of(true)),  // Simule un retour "true" de l'Observable
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule,
      ],
      declarations: [AppComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create the app', () => {
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should call logOut and navigate on logout()', fakeAsync(() => {
    const logOutSpy = jest.spyOn(sessionService, 'logOut');
    const routerSpy = jest.spyOn(router, 'navigate');

    // Appeler la méthode logout
    component.logout();

    // Simuler l'écoulement du temps (comme pour flush())
    flush();

    // Vérifier que les méthodes ont été appelées
    expect(logOutSpy).toHaveBeenCalled();
    expect(routerSpy).toHaveBeenCalledWith(['']);
  }));
});
