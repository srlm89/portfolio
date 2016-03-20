package xxl.java.container.classic;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static xxl.java.container.classic.EasyIterable.asIterable;
import static xxl.java.container.classic.EasyIterable.size;

import java.util.Enumeration;

import org.junit.Test;

public class EasyIterableTest {

	@Test
	public void sizeOfIterable() {
		assertEquals(0, size(asList()));
		assertEquals(3, size(asList(null, new Object(), 9)));
	}
	
	@Test
	@SuppressWarnings("unused")
	public void iterableFromEnumeration() {
		final int capacity = 20;
		final int[] count = new int[] {0};
		final StringBuilder builder = new StringBuilder(capacity);
		Enumeration<String> enumeration = new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() {
				return count[0] < capacity;
			}

			@Override
			public String nextElement() {
				count[0]++;
				builder.append('!');
				return builder.toString();
			}
		};
		
		String last = "";
		Iterable<String> iterable = asIterable(enumeration);
		for (String partial : iterable) {
			last = partial;
		}
		
		assertEquals(capacity, last.length());
		assertTrue(last.matches("^!+$"));
		
		for (String partial : iterable) {
			fail("Enumeration should already be consumed");
		}
		
		try {
			iterable.iterator().remove();
		} catch (UnsupportedOperationException e) {
			return;
		}
		fail("Enumeration does not allow to remove objects");
	}
}
