public class FriendlyGreeter implements Greeter{
    @Override
    public String greet(String name){
        return "It's great to meet you "+name;
    }
}
