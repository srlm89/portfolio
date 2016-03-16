package test.dynamic.compiler;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HelloWorldMessageTest {

    @Test
    public void protectedMethodTest() {
        assertEquals("Hello World!", new HelloWorldMessage().message());
    }
}

