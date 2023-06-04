import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITranscript } from '../transcript.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../transcript.test-samples';

import { TranscriptService, RestTranscript } from './transcript.service';

const requireRestSample: RestTranscript = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.format(DATE_FORMAT),
};

describe('Transcript Service', () => {
  let service: TranscriptService;
  let httpMock: HttpTestingController;
  let expectedResult: ITranscript | ITranscript[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TranscriptService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Transcript', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const transcript = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(transcript).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Transcript', () => {
      const transcript = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(transcript).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Transcript', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Transcript', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Transcript', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTranscriptToCollectionIfMissing', () => {
      it('should add a Transcript to an empty array', () => {
        const transcript: ITranscript = sampleWithRequiredData;
        expectedResult = service.addTranscriptToCollectionIfMissing([], transcript);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transcript);
      });

      it('should not add a Transcript to an array that contains it', () => {
        const transcript: ITranscript = sampleWithRequiredData;
        const transcriptCollection: ITranscript[] = [
          {
            ...transcript,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTranscriptToCollectionIfMissing(transcriptCollection, transcript);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Transcript to an array that doesn't contain it", () => {
        const transcript: ITranscript = sampleWithRequiredData;
        const transcriptCollection: ITranscript[] = [sampleWithPartialData];
        expectedResult = service.addTranscriptToCollectionIfMissing(transcriptCollection, transcript);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transcript);
      });

      it('should add only unique Transcript to an array', () => {
        const transcriptArray: ITranscript[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const transcriptCollection: ITranscript[] = [sampleWithRequiredData];
        expectedResult = service.addTranscriptToCollectionIfMissing(transcriptCollection, ...transcriptArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transcript: ITranscript = sampleWithRequiredData;
        const transcript2: ITranscript = sampleWithPartialData;
        expectedResult = service.addTranscriptToCollectionIfMissing([], transcript, transcript2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transcript);
        expect(expectedResult).toContain(transcript2);
      });

      it('should accept null and undefined values', () => {
        const transcript: ITranscript = sampleWithRequiredData;
        expectedResult = service.addTranscriptToCollectionIfMissing([], null, transcript, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transcript);
      });

      it('should return initial array if no Transcript is added', () => {
        const transcriptCollection: ITranscript[] = [sampleWithRequiredData];
        expectedResult = service.addTranscriptToCollectionIfMissing(transcriptCollection, undefined, null);
        expect(expectedResult).toEqual(transcriptCollection);
      });
    });

    describe('compareTranscript', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTranscript(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTranscript(entity1, entity2);
        const compareResult2 = service.compareTranscript(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTranscript(entity1, entity2);
        const compareResult2 = service.compareTranscript(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTranscript(entity1, entity2);
        const compareResult2 = service.compareTranscript(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
