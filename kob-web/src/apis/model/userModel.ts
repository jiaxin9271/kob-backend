export interface getTokenParams {
  username: string;
  password: string;
}

export interface registerParams {
  username: string;
  password: string;
  confirmedPassword: string;
}

export interface addBotParams {
  title: string;
  description: string;
  content: string;
}

export interface updateBotParams {
  bot_id: number;
  title: string;
  description: string;
  content: string;
}

export interface removeBotParams {
  bot_id: number;
}

export interface acwingRceiveCodeParams {
  code: string;
  state: string;
}
