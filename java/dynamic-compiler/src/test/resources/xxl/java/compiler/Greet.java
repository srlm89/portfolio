package test.dynamic.greetings;

public class Greet {

    public Greet(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Hello my dearest, " + object;
    }

    private Object object;
}

