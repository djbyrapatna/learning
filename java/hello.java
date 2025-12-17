public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, Java is alive.");
        Person p = new Person("Dhruva",25);
        System.out.println(p.getName());
        System.out.println(p.getAge());
        p.haveBirthday();
        System.out.println(p.getAge());

        Greeter g = new FriendlyGreeter();
        System.out.println(g.greet("Dhruva"));
        Greeter gf = new FormalGreeter();
        System.out.println(gf.greet("Dhruva"));

       
    }
}
