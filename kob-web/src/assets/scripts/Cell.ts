export class Cell {
  r: any;
  c: any;
  x: any;
  y: any;

  constructor(r: number, c: number) {
    this.r = r;
    this.c = c;
    this.x = c + 0.5;
    this.y = r + 0.5;
  }
}
