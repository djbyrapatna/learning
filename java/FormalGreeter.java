public class FormalGreeter implements Greeter{
    @Override
    public String greet(String name){
        return "Salutations "+name;
    }
}
