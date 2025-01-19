import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetailComponent } from '../features/sessions/components/detail/detail.component';
import { SessionApiService } from '../features/sessions/services/session-api.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { expect } from '@jest/globals';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { SessionService } from '../services/session.service';

describe('DetailComponent Integration Test', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let sessionApiService: SessionApiService;
  let router: Router;
  let matSnackBar: MatSnackBar;

  beforeEach(async () => {
    const matSnackBarMock = {
      open: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [DetailComponent],
      providers: [
        { provide: Router, useValue: { navigate: jest.fn() } },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: () => '1'
              }
            }
          }
        },
        {
          provide: SessionApiService,
          useValue: {
            detail: jest.fn().mockReturnValue(of({
              id: 1,
              users: [],
              teacher_id: 1,
              name: 'Nom de la session',
              description: 'Description de la session',
              date: new Date()
            })),
            delete: jest.fn().mockReturnValue(of({})),
            sessionInformation: { admin: true, id: 1 }
          }
        },
        {
          provide: SessionService,
          useValue: {
            sessionInformation: { admin: true, id: 1 },
            isLogged: true,
            $isLogged: jest.fn().mockReturnValue(of(true))
          }
        }
      ],
      imports: [HttpClientTestingModule, ReactiveFormsModule, MatSnackBarModule],
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    sessionApiService = TestBed.inject(SessionApiService);
    router = TestBed.inject(Router);
    matSnackBar = TestBed.inject(MatSnackBar);
    fixture.detectChanges();
  });

  it('should fetch session details on init', () => {
    component.ngOnInit();

    expect(component.session).toBeDefined();
    expect(component.session?.description).toBe('Description de la session');
  });

  it('should delete the session and navigate to sessions', () => {
    jest.spyOn(sessionApiService, 'delete').mockReturnValue(of({}));

    component.delete();

    expect(matSnackBar.open).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
    expect(router.navigate).toHaveBeenCalledWith(['sessions']);
  });
}); 