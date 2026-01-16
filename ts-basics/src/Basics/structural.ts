type A = {name: string, age: number};
type B = {name: string, age: number};


function structuralTyping(aObject: A): B {
    return aObject;
}

const a: A = {name: "Dhruva", age: 25};
const b: B = structuralTyping(a);

console.log(b);
