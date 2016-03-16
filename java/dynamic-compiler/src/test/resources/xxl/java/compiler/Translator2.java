package test.dynamic.compiler;

public class Translator {

    public static String translate(String message) {
        if (message.equalsIgnoreCase("hola"))
            return "hello";
        if (message.equalsIgnoreCase("saludos"))
            return "greetings";
        return "unknown word";
    }
}

