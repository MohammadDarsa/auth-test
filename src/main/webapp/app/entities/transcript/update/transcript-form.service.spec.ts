import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../transcript.test-samples';

import { TranscriptFormService } from './transcript-form.service';

describe('Transcript Form Service', () => {
  let service: TranscriptFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TranscriptFormService);
  });

  describe('Service methods', () => {
    describe('createTranscriptFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTranscriptFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            language: expect.any(Object),
            year: expect.any(Object),
            status: expect.any(Object),
            comment: expect.any(Object),
            date: expect.any(Object),
            student: expect.any(Object),
          })
        );
      });

      it('passing ITranscript should create a new form with FormGroup', () => {
        const formGroup = service.createTranscriptFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            language: expect.any(Object),
            year: expect.any(Object),
            status: expect.any(Object),
            comment: expect.any(Object),
            date: expect.any(Object),
            student: expect.any(Object),
          })
        );
      });
    });

    describe('getTranscript', () => {
      it('should return NewTranscript for default Transcript initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTranscriptFormGroup(sampleWithNewData);

        const transcript = service.getTranscript(formGroup) as any;

        expect(transcript).toMatchObject(sampleWithNewData);
      });

      it('should return NewTranscript for empty Transcript initial value', () => {
        const formGroup = service.createTranscriptFormGroup();

        const transcript = service.getTranscript(formGroup) as any;

        expect(transcript).toMatchObject({});
      });

      it('should return ITranscript', () => {
        const formGroup = service.createTranscriptFormGroup(sampleWithRequiredData);

        const transcript = service.getTranscript(formGroup) as any;

        expect(transcript).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITranscript should not enable id FormControl', () => {
        const formGroup = service.createTranscriptFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTranscript should disable id FormControl', () => {
        const formGroup = service.createTranscriptFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
