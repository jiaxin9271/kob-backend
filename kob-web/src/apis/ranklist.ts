import HttpClient from '../utils/axios';
import type { getRankListParams } from './model/ranklistModel';

export const getRankList = (params: getRankListParams) => {
  return HttpClient.get('/api/ranklist/getlist', {params: {...params}});
};