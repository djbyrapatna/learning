type result <T> =
    | {ok: true; value: T}
    | {ok: false, error: string};

function wrap<T>(value: T): result<T>{
    return {ok: true, value: value};
}

function fail<T>(msg: string): result<T>{
    return {ok:false, error: msg};
}

function unwrap<T>(r: result<T>): T{
    if(! r.ok){
        throw new Error("Invalid results");
    }
    return r.value;
}

const aT = wrap(123);
const bT = fail<number>("nope");

console.log(unwrap(aT));
console.log(unwrap(bT)); 
