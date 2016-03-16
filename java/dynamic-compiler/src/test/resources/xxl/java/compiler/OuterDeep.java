package test.dynamic.compiler;

public class OuterDeep {

    public class Inner {

        public class InnerInner {

            @Override
            public String toString() {
                return "Hello from second inner class!";
            }
        }
    }
}

