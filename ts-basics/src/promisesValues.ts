function addPromise(a: number, b: number): Promise<number>{
    return new Promise((resolve) => {
        setTimeout(()=>{}, 500);
        const c= a+b;
        resolve(c);
    })
}

addPromise(2, 3).then((sum) => {
  console.log(sum); // should log 5 after ~500ms
});
