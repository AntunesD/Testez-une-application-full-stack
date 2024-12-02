import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of } from 'rxjs'; // Pour retourner un Observable simulé

import { AppComponent } from './app.component';
import { SessionService } from './services/session.service';

describe('AppComponent', () => {
  let sessionService: SessionService;
  let fixture: ComponentFixture<AppComponent>;
  let component: AppComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        // Mock du SessionService
        { provide: SessionService, useValue: { $isLogged: jest.fn() } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    sessionService = TestBed.inject(SessionService);
  });

  it('should create the app', () => {
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should return observable from $isLogged()', () => {
    // Simuler le retour d'un Observable
    const mockObservable = of(true);
    jest.spyOn(sessionService, '$isLogged').mockReturnValue(mockObservable);

    // Appeler la méthode $isLogged et vérifier qu'elle retourne bien un observable
    component.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(true);  // Vérifier que la valeur retournée est bien `true`
    });
  });
});
