package test.dynamic.compiler;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HelloWorldTest {

    @Test
    public void toStringTest() {
        assertEquals("Hello World!", new HelloWorld().toString());
    }
}

