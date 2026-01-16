function findUser(uid: string): string | undefined{
    let user: string;
    if(uid == "admin"){
        user = "Dhruva";
        
    } 

    return user;
}

const user = findUser("guest");

if (user !== undefined) {
  console.log(user.toUpperCase());
} else{
    console.log("guest");
}
