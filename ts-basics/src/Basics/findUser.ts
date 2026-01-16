function findUserName(id: string): string | undefined{
    if(id =="admin") return "Dhruva";
    return undefined;
}

const username = findUserName("Sai");

console.log(username);
if (typeof username === "string"){
    console.log(username.toUpperCase);
}
