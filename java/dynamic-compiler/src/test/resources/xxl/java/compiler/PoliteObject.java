package test.dynamic.compiler;

import test.dynamic.greetings.Greet;

public class PoliteObject {

    public String greet(Object object) {
        return new Greet(object).toString();
    }
}

