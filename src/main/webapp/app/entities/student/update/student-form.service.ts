import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStudent, NewStudent } from '../student.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStudent for edit and NewStudentFormGroupInput for create.
 */
type StudentFormGroupInput = IStudent | PartialWithRequiredKeyOf<NewStudent>;

type StudentFormDefaults = Pick<NewStudent, 'id'>;

type StudentFormGroupContent = {
  id: FormControl<IStudent['id'] | NewStudent['id']>;
  email: FormControl<IStudent['email']>;
  name: FormControl<IStudent['name']>;
  gender: FormControl<IStudent['gender']>;
  major: FormControl<IStudent['major']>;
  year: FormControl<IStudent['year']>;
  nameAr: FormControl<IStudent['nameAr']>;
  placeOfBirthEn: FormControl<IStudent['placeOfBirthEn']>;
  placeOfBirthAr: FormControl<IStudent['placeOfBirthAr']>;
  dateOfBirthEn: FormControl<IStudent['dateOfBirthEn']>;
  dateOfBirthAr: FormControl<IStudent['dateOfBirthAr']>;
  nationality: FormControl<IStudent['nationality']>;
  phone: FormControl<IStudent['phone']>;
};

export type StudentFormGroup = FormGroup<StudentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StudentFormService {
  createStudentFormGroup(student: StudentFormGroupInput = { id: null }): StudentFormGroup {
    const studentRawValue = {
      ...this.getFormDefaults(),
      ...student,
    };
    return new FormGroup<StudentFormGroupContent>({
      id: new FormControl(
        { value: studentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      email: new FormControl(studentRawValue.email),
      name: new FormControl(studentRawValue.name),
      gender: new FormControl(studentRawValue.gender),
      major: new FormControl(studentRawValue.major),
      year: new FormControl(studentRawValue.year),
      nameAr: new FormControl(studentRawValue.nameAr),
      placeOfBirthEn: new FormControl(studentRawValue.placeOfBirthEn),
      placeOfBirthAr: new FormControl(studentRawValue.placeOfBirthAr),
      dateOfBirthEn: new FormControl(studentRawValue.dateOfBirthEn),
      dateOfBirthAr: new FormControl(studentRawValue.dateOfBirthAr),
      nationality: new FormControl(studentRawValue.nationality),
      phone: new FormControl(studentRawValue.phone),
    });
  }

  getStudent(form: StudentFormGroup): IStudent | NewStudent {
    return form.getRawValue() as IStudent | NewStudent;
  }

  resetForm(form: StudentFormGroup, student: StudentFormGroupInput): void {
    const studentRawValue = { ...this.getFormDefaults(), ...student };
    form.reset(
      {
        ...studentRawValue,
        id: { value: studentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): StudentFormDefaults {
    return {
      id: null,
    };
  }
}
