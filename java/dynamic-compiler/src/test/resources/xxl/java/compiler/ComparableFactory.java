package test.dynamic.compiler;

public class ComparableFactory {

    public static Comparable<String> newComparable() {
        return new Comparable<String>() {
            @Override
            public int compareTo(String string) {
                return string.length() % 2;
            }
        };
    }
}

