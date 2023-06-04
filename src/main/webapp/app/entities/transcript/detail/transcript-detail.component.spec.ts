import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TranscriptDetailComponent } from './transcript-detail.component';

describe('Transcript Management Detail Component', () => {
  let comp: TranscriptDetailComponent;
  let fixture: ComponentFixture<TranscriptDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TranscriptDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ transcript: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TranscriptDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TranscriptDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load transcript on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.transcript).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
