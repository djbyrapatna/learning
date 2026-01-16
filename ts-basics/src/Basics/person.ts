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

function haveBirthdayMutable(p: Person): Person {
    p.age +=1;
    return p;
}

function haveBirthdayDestructuring(p: Person): Person {
    const {name, age} = p;
    return {name, age: age+1};

}

const p1: Person = {name: "Dhruva", age:25}
const p2: Person = haveBirthday(p1);

console.log(p1, p2);
const p3: Person = haveBirthdayMutable(p1);
console.log(p1, p3);
const p4: Person = haveBirthdayDestructuring(p1);
console.log(p1, p4);
