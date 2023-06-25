import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITranscript, NewTranscript } from '../transcript.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITranscript for edit and NewTranscriptFormGroupInput for create.
 */
type TranscriptFormGroupInput = ITranscript | PartialWithRequiredKeyOf<NewTranscript>;

type TranscriptFormDefaults = Pick<NewTranscript, 'id'>;

type TranscriptFormGroupContent = {
  id: FormControl<ITranscript['id'] | NewTranscript['id']>;
  language: FormControl<ITranscript['language']>;
  year: FormControl<ITranscript['year']>;
  status: FormControl<ITranscript['status']>;
  type: FormControl<ITranscript['type']>;
  comment: FormControl<ITranscript['comment']>;
  date: FormControl<ITranscript['date']>;
  student: FormControl<ITranscript['student']>;
};

export type TranscriptFormGroup = FormGroup<TranscriptFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TranscriptFormService {
  createTranscriptFormGroup(transcript: TranscriptFormGroupInput = { id: null }): TranscriptFormGroup {
    const transcriptRawValue = {
      ...this.getFormDefaults(),
      ...transcript,
    };
    return new FormGroup<TranscriptFormGroupContent>({
      id: new FormControl(
        { value: transcriptRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      language: new FormControl(transcriptRawValue.language),
      year: new FormControl(transcriptRawValue.year),
      status: new FormControl(transcriptRawValue.status),
      type: new FormControl(transcriptRawValue.type),
      comment: new FormControl(transcriptRawValue.comment),
      date: new FormControl(transcriptRawValue.date),
      student: new FormControl(transcriptRawValue.student),
    });
  }

  getTranscript(form: TranscriptFormGroup): ITranscript | NewTranscript {
    return form.getRawValue() as ITranscript | NewTranscript;
  }

  resetForm(form: TranscriptFormGroup, transcript: TranscriptFormGroupInput): void {
    const transcriptRawValue = { ...this.getFormDefaults(), ...transcript };
    form.reset(
      {
        ...transcriptRawValue,
        id: { value: transcriptRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TranscriptFormDefaults {
    return {
      id: null,
    };
  }
}
