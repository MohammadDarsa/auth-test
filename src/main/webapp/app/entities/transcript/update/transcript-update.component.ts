import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TranscriptFormService, TranscriptFormGroup } from './transcript-form.service';
import { ITranscript } from '../transcript.model';
import { TranscriptService } from '../service/transcript.service';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';

@Component({
  selector: 'jhi-transcript-update',
  templateUrl: './transcript-update.component.html',
})
export class TranscriptUpdateComponent implements OnInit {
  isSaving = false;
  transcript: ITranscript | null = null;

  studentsSharedCollection: IStudent[] = [];

  editForm: TranscriptFormGroup = this.transcriptFormService.createTranscriptFormGroup();

  constructor(
    protected transcriptService: TranscriptService,
    protected transcriptFormService: TranscriptFormService,
    protected studentService: StudentService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareStudent = (o1: IStudent | null, o2: IStudent | null): boolean => this.studentService.compareStudent(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transcript }) => {
      this.transcript = transcript;
      if (transcript) {
        this.updateForm(transcript);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transcript = this.transcriptFormService.getTranscript(this.editForm);
    if (transcript.id !== null) {
      this.subscribeToSaveResponse(this.transcriptService.update(transcript));
    } else {
      this.subscribeToSaveResponse(this.transcriptService.create(transcript));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITranscript>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(transcript: ITranscript): void {
    this.transcript = transcript;
    this.transcriptFormService.resetForm(this.editForm, transcript);

    this.studentsSharedCollection = this.studentService.addStudentToCollectionIfMissing<IStudent>(
      this.studentsSharedCollection,
      transcript.student
    );
  }

  protected loadRelationshipsOptions(): void {
    this.studentService
      .query()
      .pipe(map((res: HttpResponse<IStudent[]>) => res.body ?? []))
      .pipe(
        map((students: IStudent[]) => this.studentService.addStudentToCollectionIfMissing<IStudent>(students, this.transcript?.student))
      )
      .subscribe((students: IStudent[]) => (this.studentsSharedCollection = students));
  }
}
