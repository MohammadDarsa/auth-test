import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TranscriptService } from '../service/transcript.service';

import { TranscriptComponent } from './transcript.component';

describe('Transcript Management Component', () => {
  let comp: TranscriptComponent;
  let fixture: ComponentFixture<TranscriptComponent>;
  let service: TranscriptService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'transcript', component: TranscriptComponent }]), HttpClientTestingModule],
      declarations: [TranscriptComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(TranscriptComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TranscriptComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TranscriptService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.transcripts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to transcriptService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTranscriptIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTranscriptIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
