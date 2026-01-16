type Input = number | undefined;

function safeDouble(input: Input): number | null {
    if (typeof(input)==="number"){
        return input *2;
    } else{
        return null;
    }
}

const result1 = safeDouble(5);
console.log(result1);

const result2 = safeDouble(undefined);
console.log(result2);
