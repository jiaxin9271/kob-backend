const AC_GAME_OBJECTS: AcGameObject[] = [];

export class AcGameObject {
  timedelta: number;
  has_called_start: boolean;
  
  constructor() {
    AC_GAME_OBJECTS.push(this);
    this.timedelta = 0;
    this.has_called_start = false;
  }

  start() {
    // 只执行一次
  }

  update() {
    // 每一帧执行一次，除了第一帧之外
  }

  on_destroy() {
    // 删除之前执行
  }

  destroy() {
    this.on_destroy();

    for (let i in AC_GAME_OBJECTS) {
      const obj = AC_GAME_OBJECTS[i];
      if (obj === this) {
        AC_GAME_OBJECTS.splice(Number(i));
        break;
      }
    }
  }
}

let last_timestamp: number; // 上一次执行的时刻
const step = (timestamp: number) => {
  for (let obj of AC_GAME_OBJECTS) {
    if (!obj.has_called_start) {
      obj.has_called_start = true;
      obj.start();
    } else {
      obj.timedelta = timestamp - last_timestamp;
      obj.update();
    }
  }

  last_timestamp = timestamp;
  requestAnimationFrame(step);
};

requestAnimationFrame(step);
