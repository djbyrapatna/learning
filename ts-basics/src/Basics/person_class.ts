class PersonClass {
    private age: number;
    constructor(public name:string, age:number){
        this.age = age;
    }
    getAge(): number{
        return this.age;
    }
    hasBirthday(): void{
        this.age+=1;
    }

}

const p = new PersonClass("Dhruva Byrapatna", 25);
p.hasBirthday();
console.log(p.getAge());
console.log(p.name);
