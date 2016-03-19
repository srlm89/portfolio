package xxl.java.primitive;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.primitive.EasyObject.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import xxl.java.primitive.EasyObject;
import xxl.java.primitive.EasyObject.Function;

public class EasyObjectTest {

	@Test
	public void methodToStringReturnsString() {
		assertEquals("1", methodToString().outputFor(1));
		assertEquals("true", methodToString().outputFor(true));
		assertEquals("null", methodToString().outputFor(null));
		assertEquals(getClass().toString(), methodToString().outputFor(getClass()));
	}

	@Test
	public void methodIdentityReturnsSameObject() {
		Integer three = 3;
		Function<Object, Integer> identity = methodIdentity(three);
		assertEquals(three, identity.outputFor("1"));
		assertEquals(three, identity.outputFor(2));
		three = null;
		assertEquals(Integer.valueOf(3), identity.outputFor(null));
		assertTrue(null == methodIdentity(three).outputFor("1"));
		assertTrue(null == methodIdentity(null).outputFor("1"));
	}

	@Test
	public void methodYourselfReturnsArgument() {
		for (Object object : asList(1, 2, true, "AAA", 'c')) {
			assertTrue(object == methodYourself().outputFor(object));
		}
	}

	@Test
	public void easyToString() {
		assertEquals("Object", asString(new Object()));
		String string = "abcd";
		Map<String, Object> fields = new LinkedHashMap<String, Object>();
		fields.put("value", string);
		fields.put("length", 4);
		fields.put("parent", null);
		assertEquals("String [value=abcd, length=4, parent=null]", asString(string, fields));
	}

	@Test
	public void easyEquals() {
		Object a = "a";
		Object b = "b";
		Object c = 20;
		assertTrue(EasyObject.equals(a, null, a, null));
		assertTrue(EasyObject.equals(a, asList(), a, asList()));
		assertTrue(EasyObject.equals(null, asList(), null, asList()));
		assertTrue(EasyObject.equals(a, asList(), b, asList()));
		assertTrue(EasyObject.equals(a, asList(10, 20), b, asList(10, 20)));
		assertTrue(EasyObject.equals(new Object(), asList(10, 20), new Object(), asList(10, 20)));
		assertFalse(EasyObject.equals(a, asList(), c, asList()));
		assertFalse(EasyObject.equals(a, asList(), null, asList()));
		assertFalse(EasyObject.equals(new Object(), asList(20, 10), new Object(), asList(10, 20)));
	}

	@Test
	public void easyEqualsDistinctListsFail() {
		boolean thrown = false;
		try {
			EasyObject.equals(new Object(), asList(1, 2), new Object(), asList(1));
		}
		catch (IllegalArgumentException i) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@Test
	public void easyHashCode() {
		Object a = new Object();
		assertTrue(hashCodeWith(a.getClass().getName()) == hashCodeWith("java.lang.Object"));
		assertTrue(hashCodeWith(null, a.getClass().getName()) == hashCodeWith(null, "java.lang.Object"));
	}
}
