import { AcGameObject } from './AcGameObject';

export class Wall extends AcGameObject {
  r: number;
  c: number;
  gamemap: any;
  color: string;

  constructor(r: number, c: number, gamemap: any) {
    super();
    this.r = r;
    this.c = c;
    this.gamemap = gamemap;
    this.color = '#B37226';
  }

  update() {
    this.render();
  }

  render() {
    const L = this.gamemap.L;
    const ctx = this.gamemap.ctx;
    ctx.fillStyle = this.color;
    ctx.fillRect(this.c * L, this.r * L, L, L);
  }
}
