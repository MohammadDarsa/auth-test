import { Gender } from 'app/entities/enumerations/gender.model';

import { IStudent, NewStudent } from './student.model';

export const sampleWithRequiredData: IStudent = {
  id: 23105,
};

export const sampleWithPartialData: IStudent = {
  id: 88178,
  email: 'Leora33@yahoo.com',
  name: 'transmit',
  major: 'hard Home',
  year: 75764,
  placeOfBirthAr: 'deploy of',
  nationality: 'auxiliary Tools Savings',
};

export const sampleWithFullData: IStudent = {
  id: 5470,
  email: 'Magali17@yahoo.com',
  name: 'Ball GB Money',
  gender: Gender['MALE'],
  major: 'users indexing',
  year: 62785,
  nameAr: 'Direct Clothing',
  placeOfBirthEn: 'impactful primary Kids',
  placeOfBirthAr: 'Synergized seamless Jewelery',
  dateOfBirthEn: 'navigate Handmade',
  dateOfBirthAr: 'Dinar magenta overriding',
  nationality: 'magenta',
  phone: '1-976-812-9538',
};

export const sampleWithNewData: NewStudent = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
