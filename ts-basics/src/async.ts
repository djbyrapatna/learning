import { delay } from "./promises";


async function asyncAdd(a: number, b:number): Promise<number>{
    await delay(500);
    return a+b;
}

async function main() {
  const sum = await asyncAdd(2, 3);
  console.log(sum); // 5
}

main();
