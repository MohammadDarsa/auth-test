import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TranscriptFormService } from './transcript-form.service';
import { TranscriptService } from '../service/transcript.service';
import { ITranscript } from '../transcript.model';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';

import { TranscriptUpdateComponent } from './transcript-update.component';

describe('Transcript Management Update Component', () => {
  let comp: TranscriptUpdateComponent;
  let fixture: ComponentFixture<TranscriptUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let transcriptFormService: TranscriptFormService;
  let transcriptService: TranscriptService;
  let studentService: StudentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TranscriptUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TranscriptUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TranscriptUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    transcriptFormService = TestBed.inject(TranscriptFormService);
    transcriptService = TestBed.inject(TranscriptService);
    studentService = TestBed.inject(StudentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Student query and add missing value', () => {
      const transcript: ITranscript = { id: 456 };
      const student: IStudent = { id: 1720 };
      transcript.student = student;

      const studentCollection: IStudent[] = [{ id: 18022 }];
      jest.spyOn(studentService, 'query').mockReturnValue(of(new HttpResponse({ body: studentCollection })));
      const additionalStudents = [student];
      const expectedCollection: IStudent[] = [...additionalStudents, ...studentCollection];
      jest.spyOn(studentService, 'addStudentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ transcript });
      comp.ngOnInit();

      expect(studentService.query).toHaveBeenCalled();
      expect(studentService.addStudentToCollectionIfMissing).toHaveBeenCalledWith(
        studentCollection,
        ...additionalStudents.map(expect.objectContaining)
      );
      expect(comp.studentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const transcript: ITranscript = { id: 456 };
      const student: IStudent = { id: 52156 };
      transcript.student = student;

      activatedRoute.data = of({ transcript });
      comp.ngOnInit();

      expect(comp.studentsSharedCollection).toContain(student);
      expect(comp.transcript).toEqual(transcript);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITranscript>>();
      const transcript = { id: 123 };
      jest.spyOn(transcriptFormService, 'getTranscript').mockReturnValue(transcript);
      jest.spyOn(transcriptService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transcript });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transcript }));
      saveSubject.complete();

      // THEN
      expect(transcriptFormService.getTranscript).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(transcriptService.update).toHaveBeenCalledWith(expect.objectContaining(transcript));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITranscript>>();
      const transcript = { id: 123 };
      jest.spyOn(transcriptFormService, 'getTranscript').mockReturnValue({ id: null });
      jest.spyOn(transcriptService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transcript: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: transcript }));
      saveSubject.complete();

      // THEN
      expect(transcriptFormService.getTranscript).toHaveBeenCalled();
      expect(transcriptService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITranscript>>();
      const transcript = { id: 123 };
      jest.spyOn(transcriptService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ transcript });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(transcriptService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareStudent', () => {
      it('Should forward to studentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(studentService, 'compareStudent');
        comp.compareStudent(entity, entity2);
        expect(studentService.compareStudent).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
