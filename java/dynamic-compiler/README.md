### Dynamic Compiler

A library for compiling source code with `javax.tools.ToolProvider.getSystemJavaCompiler()`, directly from `String` objects. For instance, you can compile and instantiate a class from raw source code:

```java
String qualifiedName = "test.dynamic.compiler.HelloWorld";
String code = "package test.dynamic.compiler;" +
              "public class HelloWorld {" +
              "    @Override" +
              "    public String toString() {" +
              "        return \"Hello World!\";" +
              "    }" +
              "}";
ClassLoader loader = BytecodeClassLoaderBuilder.loaderFor(qualifiedName, code);
Class<?> newClass = loader.loadClass(qualifiedName);
Object newInstance = newClass.newInstance();
String helloWorld = newInstance.toString();
System.out.pritnln(helloWorld);
```

