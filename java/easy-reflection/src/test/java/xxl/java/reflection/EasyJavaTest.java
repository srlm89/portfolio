package xxl.java.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class EasyJavaTest {

	public static class Inner {
		public static class NestedInner {

		}
	}

	@Test
	public void classNameFromQualifiedName() {
		assertEquals("", EasyJava.simpleClassName(""));
		assertEquals("HelloWorld", EasyJava.simpleClassName("HelloWorld"));
		assertEquals("HelloWorld", EasyJava.simpleClassName("java.HelloWorld"));
		assertEquals("HelloWorld", EasyJava.simpleClassName("java.api.HelloWorld"));
		assertEquals("Greeting", EasyJava.simpleClassName("HelloWorld$Greeting"));
		assertEquals("Greeting", EasyJava.simpleClassName("java.api.HelloWorld$Greeting"));
		assertEquals("Inner", EasyJava.simpleClassName(Inner.class.getName()));
		assertEquals("Inner", EasyJava.simpleClassName(Inner.class.getSimpleName()));
		assertEquals("Inner", EasyJava.simpleClassName(Inner.class.getCanonicalName()));
		assertEquals("NestedInner", EasyJava.simpleClassName(Inner.NestedInner.class.getName()));
		assertEquals("NestedInner", EasyJava.simpleClassName(Inner.NestedInner.class.getSimpleName()));
		assertEquals("NestedInner", EasyJava.simpleClassName(Inner.NestedInner.class.getCanonicalName()));
	}

	@Test
	public void packageNameFromQualifiedName() {
		assertEquals("", EasyJava.packageName(""));
		assertEquals("", EasyJava.packageName("HelloWorld"));
		assertEquals("", EasyJava.packageName("HelloWorld$Greeting"));
		assertEquals("java", EasyJava.packageName("java.HelloWorld"));
		assertEquals("java.api", EasyJava.packageName("java.api.HelloWorld"));
		assertEquals("java.api", EasyJava.packageName("java.api.HelloWorld$Greeting"));
		assertEquals("", EasyJava.packageName(Inner.class.getSimpleName()));
		assertEquals("", EasyJava.packageName(Inner.NestedInner.class.getSimpleName()));
		assertEquals("xxl.java.reflection", EasyJava.packageName(Inner.class.getName()));
		assertEquals("xxl.java.reflection", EasyJava.packageName(Inner.NestedInner.class.getName()));
	}

	@Test
	public void absolutePath() throws IOException {
		File workingDirectory = new File(".");
		String folderSeparator = EasyJava.folderPathSeparator();
		assertFalse(workingDirectory.getAbsolutePath().endsWith(folderSeparator));
	}

	@Test
	public void joinClasspaths() throws MalformedURLException {
		URL url = new URL("file:///imaginary/project/folder/src");
		URL url2 = new URL("file:///imaginary/dependency/lib.jar");
		String classpath = EasyJava.asClasspath(new URL[] { url, url2 });
		Character classPathSeparator = File.pathSeparatorChar;
		assertEquals("/imaginary/project/folder/src" + classPathSeparator + "/imaginary/dependency/lib.jar", classpath);
	}

	@Test
	public void filePathFromClasspath() throws MalformedURLException {
		URL url = new URL("file:///imaginary/project/folder/src");
		URL url2 = new URL("file:///imaginary/dependency/lib.jar");
		URL[] classpath = new URL[] { url, url2 };
		String[] filePath = EasyJava.asFilePath(classpath);
		assertTrue(2 == filePath.length);
		assertEquals("/imaginary/project/folder/src", filePath[0]);
		assertEquals("/imaginary/dependency/lib.jar", filePath[1]);
	}

	@Test
	public void loadingClassFromClasspath() throws Exception {
		String qualifiedName = "xxl.java.library.HelloWorld";
		File jar = resource("HelloWorld.jar");
		Class<?> helloWorldClass = EasyJava.classFromClasspath(jar.toURI().toURL(), qualifiedName);
		assertTrue(helloWorldClass != null);
		Method greet = helloWorldClass.getMethod("greet");
		assertEquals("Hello, world!", greet.invoke(helloWorldClass.newInstance()));
	}
	
	private File resource(String name) {
		URL url = getClass().getResource(name);
		return new File(url.getFile());
	}
}
