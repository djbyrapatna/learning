type Person = {
    name: string;
    age: number;
} ;

type Person2 = {
    name: string;
    age: number;
};

function haveBirthday(p: Person): Person2{
    return {
        name: p.name,
        age: p.age+1,
    };
}

const p1: Person = {name: "Dhruva", age:25}
const p2: Person = haveBirthday(p1);

console.log(p1, p2);
