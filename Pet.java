public class Pet {
    private String name;
    private int age;

    // constructors
    public Pet(){
        name = "";
        age = 0;
    }
    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // accessor and mutator methods
    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
