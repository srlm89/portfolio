package test.dynamic.compiler;

import test.dynamic.newjar.Printer;

public class HelloWorld {

    @Override
    public String toString() {
        return new Printer().print(this);
    }
}

