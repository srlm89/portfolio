package test.dynamic.compiler.client;

import static test.dynamic.compiler.dependency.Echo.*;

public class Client {

    @Override
    public String toString() {
        return echo("response");
    }
}

