import dayjs from 'dayjs/esm';
import { IStudent } from 'app/entities/student/student.model';

export interface ITranscript {
  id: number;
  language?: string | null;
  year?: number | null;
  status?: string | null;
  comment?: string | null;
  date?: dayjs.Dayjs | null;
  student?: Pick<IStudent, 'id'> | null;
}

export type NewTranscript = Omit<ITranscript, 'id'> & { id: null };
