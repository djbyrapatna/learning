

type Loading = {state: "loading"};
type Success = {state: "success"; data: string};
type ErrorMessage = {state: "error"; data: string};
type APIStatus = Loading | Success | ErrorMessage;

function logState(s: APIStatus): void{
    if (s.state === "loading"){
        console.log("Loading");
    } else if (s.state ==="success"){
        console.log(s.data);
    } else {
        console.log("Error: ", s.data);
    }

}

const load: APIStatus = {state: "loading"};
const successMessage: APIStatus = {state: "success", data: "20 responses received"};
const errorMessage: APIStatus = {state: "error", data: "401 request timeout"};

logState(load);
logState(successMessage);
logState(errorMessage);


