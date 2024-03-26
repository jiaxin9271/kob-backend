import { defineStore } from 'pinia';

export const useRecordStore = defineStore('record', {
  state: () => {
    return {
      is_record: false,
      a_steps: '',
      b_steps: '',
      record_loser: '',
    };
  },
  getters: {},
  actions: {
    updateIsRecord(is_record: boolean) {
      this.is_record = is_record;
    },
    updateSteps(data: any) {
      this.a_steps = data.a_steps;
      this.b_steps = data.b_steps;
    },
    updateRecordLoser(loser: string) {
      this.record_loser = loser;
    },
  },
});
