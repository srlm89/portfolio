package xxl.java.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JarPackageTest {

	@Test
	public void classPathFromQualifiedName() {
		JarPackage jar = new JarPackage();
		assertEquals("/.class", jar.toClassPath(""));
		assertEquals("/HelloWorld.class", jar.toClassPath("HelloWorld"));
		assertEquals("/HelloWorld$Greeting.class", jar.toClassPath("HelloWorld$Greeting"));
		assertEquals("java/HelloWorld.class", jar.toClassPath("java.HelloWorld"));
		assertEquals("java/api/HelloWorld.class", jar.toClassPath("java.api.HelloWorld"));
		assertEquals("java/api/HelloWorld$Greeting.class", jar.toClassPath("java.api.HelloWorld$Greeting"));
	}
	
	@Test
	public void classNameFromQualifiedName() {
		JarPackage jar = new JarPackage();
		assertEquals("", jar.className(""));
		assertEquals("HelloWorld", jar.className("HelloWorld"));
		assertEquals("HelloWorld", jar.className("java.HelloWorld"));
		assertEquals("HelloWorld", jar.className("java.api.HelloWorld"));
		assertEquals("HelloWorld$Greeting", jar.className("HelloWorld$Greeting"));
		assertEquals("HelloWorld$Greeting", jar.className("java.api.HelloWorld$Greeting"));
	}

	@Test
	public void packageNameFromQualifiedName() {
		JarPackage jar = new JarPackage();
		assertEquals("", jar.packageName(""));
		assertEquals("", jar.packageName("HelloWorld"));
		assertEquals("", jar.packageName("HelloWorld$Greeting"));
		assertEquals("java", jar.packageName("java.HelloWorld"));
		assertEquals("java.api", jar.packageName("java.api.HelloWorld"));
		assertEquals("java.api", jar.packageName("java.api.HelloWorld$Greeting"));
	}
}
