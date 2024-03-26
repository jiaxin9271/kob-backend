import HttpClient from '../utils/axios';
import type {
  getTokenParams,
  registerParams,
  addBotParams,
  updateBotParams,
  removeBotParams,
  acwingRceiveCodeParams,
} from './model/userModel';

export const getInfo = () => {
  return HttpClient.get('/api/user/account/info');
};

export const getToken = (params: getTokenParams) => {
  return HttpClient.post('/api/user/account/token', { ...params });
};

export const register = (params: registerParams) => {
  return HttpClient.post('/api/user/account/register', { ...params });
};

export const getBotList = () => {
  return HttpClient.get('/api/user/bot/getlist');
};

export const addBot = (params: addBotParams) => {
  return HttpClient.post('/api/user/bot/add', { ...params });
};

export const updateBot = (params: updateBotParams) => {
  return HttpClient.post('/api/user/bot/update', { ...params });
};

export const removeBot = (params: removeBotParams) => {
  return HttpClient.post('/api/user/bot/remove', { ...params });
};

export const acwingApplyCode = () => {
  return HttpClient.get('/api/user/account/acwing/web/apply_code');
};

export const acwingRceiveCode = (params: acwingRceiveCodeParams) => {
  return HttpClient.get('api/user/account/acwing/web/receive_code', { params: { ...params } });
};
