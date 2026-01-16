export{}

export function delay (ms: number): Promise<void>{
    return new Promise((resolve) => {
    setTimeout(resolve, ms);
    });
}

function main(){
    delay(1000).then(()=>console.log("Delay done"));
    console.log("hello");
}



