import dayjs from 'dayjs/esm';

import { ITranscript, NewTranscript } from './transcript.model';

export const sampleWithRequiredData: ITranscript = {
  id: 3745,
};

export const sampleWithPartialData: ITranscript = {
  id: 98171,
  language: 'Virginia',
  year: 48958,
  status: 'mindshare',
  date: dayjs('2023-06-03'),
};

export const sampleWithFullData: ITranscript = {
  id: 20591,
  language: 'compressing disintermediate',
  year: 58067,
  status: 'Account Sweden group',
  comment: 'Islands, generate didactic',
  date: dayjs('2023-06-02'),
};

export const sampleWithNewData: NewTranscript = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
