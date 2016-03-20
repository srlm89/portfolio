package xxl.java.reflection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

public class EasyClassTest {

	@Test
	public void classesOfObjects() {
		List<? extends Class<?>> classes = asList(String.class, EasyClassTest.class, OtherClass.class, Object.class);
		List<? extends Object> objects = asList("p", new EasyClassTest(), new OtherClass(), Object.class);
		assertEquals(classes, EasyClass.asClasses(objects));
	}

	@Test
	public void hasMethodCheck() {
		assertTrue(EasyClass.hasMethod(Class.class, "getMethod", String.class, Class[].class));
		assertTrue(EasyClass.hasMethod(String.class, "replaceAll", String.class, String.class));
		assertFalse(EasyClass.hasMethod(String.class, "replaceWithARegexOrDie", String.class));
	}

	@Test
	@SuppressWarnings("unused")
	public void hasMethodForPrivateMethod() {
		class SuperClass {
			protected void nij() {}
		};
		class SubClass extends SuperClass {
			private void nuj() {}
		};
		assertTrue(EasyClass.hasMethod(SubClass.class, "nuj"));
		assertTrue(EasyClass.hasMethod(SuperClass.class, "nij"));
		assertTrue(EasyClass.hasMethod(SubClass.class, "nij"));
	}

	@Test
	public void subclassRelationship() {
		Class<?> string = String.class;
		Class<?> object = Object.class;
		assertTrue(EasyClass.isSubclassOf(object, string));
		assertFalse(EasyClass.isSubclassOf(string, object));
		assertTrue(EasyClass.isSuperclassOf(string, object));
		assertFalse(EasyClass.isSuperclassOf(object, string));
	}

	@Test
	public void createInstanceWithReflection() {
		Class<?> objectClass = EasyClassTest.class;
		Object object = EasyClass.newInstance(objectClass);
		assertEquals(objectClass, object.getClass());
	}

	@Test
	public void comparisonWithReflection() {
		OtherClass one = new OtherClass();
		OtherClass other = new OtherClass();
		assertTrue(EasyClass.isGreaterThan(2, 1));
		assertTrue(EasyClass.isLessThan("AAA", "BBB"));
		assertFalse(EasyClass.isGreaterThan(one, other));
		assertFalse(EasyClass.isLessThan(one, other));
	}

	@Test
	public void invocationWithReflection() {
		OtherClass object = new OtherClass();
		Method one = EasyClass.method("one", object.getClass());
		Method print = EasyClass.method("print", object.getClass(), Number.class);
		assertEquals(1, EasyClass.invoke(one, object));
		assertEquals("1", EasyClass.invoke(print, object, 1));
		assertEquals("1.0", EasyClass.invoke(print, object, 1.0));
	}

	@Test
	public void invocationWithReflectionToPrivateMethod() {
		OtherClass object = new OtherClass();
		Method printWithSpaces = EasyClass.method("privatePrint", object.getClass(), Number.class);
		assertFalse(printWithSpaces.isAccessible());
		Object result = EasyClass.invokeTrespassing(printWithSpaces, object, 123);
		assertEquals("1#2#3#", result);
		assertFalse(printWithSpaces.isAccessible());
	}
}

class OtherClass {
	public int one() {
		return 1;
	}

	public String print(Number number) {
		return number.toString();
	}

	@SuppressWarnings("unused")
	private String privatePrint(Number number) {
		String toString = print(number);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < toString.length(); i += 1) {
			builder.append(toString.charAt(i) + "#");
		}
		return builder.toString();
	}
}