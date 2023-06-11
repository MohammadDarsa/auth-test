import { Gender } from 'app/entities/enumerations/gender.model';

export interface IStudent {
  id: number;
  studentId?: number | null;
  email?: string | null;
  name?: string | null;
  gender?: Gender | null;
  major?: string | null;
  year?: number | null;
  nameAr?: string | null;
  placeOfBirthEn?: string | null;
  placeOfBirthAr?: string | null;
  dateOfBirthEn?: string | null;
  dateOfBirthAr?: string | null;
  nationality?: string | null;
  phone?: string | null;
}

export type NewStudent = Omit<IStudent, 'id'> & { id: null };
