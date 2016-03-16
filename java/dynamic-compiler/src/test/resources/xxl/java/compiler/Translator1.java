package test.dynamic.compiler;

public class Translator {

    public static String translate(String message) {
        if (message.equalsIgnoreCase("hola"))
            return "hello";
        return "unknown word";
    }
}

