interface Greeter{
    greet(name: string): string;
    
}

class FriendlyGreeter implements Greeter{
    greet(name: string): string{
        return "Hello "+ name + "! It's great to meet you!";
    }
}

class FormalGreeter implements Greeter{
     greet(name: string): string{
        return `Salutations ${name}. Welcome.`;
     }
}

class AngryGreeter implements Greeter{
    greet(name: string): string {
        return `Get the fuck out ${name}!`;
    }
}

function runGreeter(g: Greeter, name: string){
    console.log(g.greet(name));
}
let testName: string = "Sai";
runGreeter(new FriendlyGreeter(), testName);
runGreeter(new FormalGreeter(), testName);
runGreeter(new AngryGreeter(), "Dhruva");

