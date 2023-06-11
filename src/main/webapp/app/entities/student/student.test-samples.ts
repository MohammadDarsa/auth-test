import { Gender } from 'app/entities/enumerations/gender.model';

import { IStudent, NewStudent } from './student.model';

export const sampleWithRequiredData: IStudent = {
  id: 23105,
};

export const sampleWithPartialData: IStudent = {
  id: 35405,
  studentId: 61242,
  email: 'Aletha76@hotmail.com',
  gender: Gender['FEMALE'],
  major: 'synergistic orchid ivory',
  placeOfBirthEn: 'Path e-markets',
  dateOfBirthAr: 'Baby B2C Shirt',
  phone: '1-723-381-2981 x662',
};

export const sampleWithFullData: IStudent = {
  id: 3024,
  studentId: 28894,
  email: 'Heloise_Ryan@yahoo.com',
  name: 'Nuevo Direct',
  gender: Gender['MALE'],
  major: 'Developer state Awesome',
  year: 59739,
  nameAr: 'compress',
  placeOfBirthEn: 'seamless',
  placeOfBirthAr: 'wireless',
  dateOfBirthEn: 'cutting-edge bypass Dinar',
  dateOfBirthAr: 'Franc',
  nationality: 'hack',
  phone: '(919) 868-1295 x3867',
};

export const sampleWithNewData: NewStudent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
