import HttpClient from '../utils/axios';
import type { getRecordListParams } from './model/recordModel';

export const getRecordList = (params: getRecordListParams) => {
  return HttpClient.get('/api/record/getlist', {params: {...params}});
};