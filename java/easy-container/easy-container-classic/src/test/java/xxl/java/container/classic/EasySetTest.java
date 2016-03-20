package xxl.java.container.classic;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static xxl.java.container.classic.EasySet.flatHashSet;
import static xxl.java.container.classic.EasySet.flatLinkedHashSet;
import static xxl.java.container.classic.EasySet.newHashSet;
import static xxl.java.container.classic.EasySet.newLinkedHashSet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.junit.Test;

public class EasySetTest {

	@Test
	public void setAddAllInOne() {
		LinkedHashSet<String> firstSet = newLinkedHashSet("a", "b", "c");
		LinkedHashSet<String> secondSet  = newLinkedHashSet("d", "e", "f");
		LinkedHashSet<String> combined = flatLinkedHashSet(firstSet, secondSet, firstSet);
		HashSet<String> hashedCombined = flatHashSet(firstSet, secondSet, firstSet);
		assertEquals(firstSet.size() + secondSet.size(), combined.size());
		assertTrue(combined.containsAll(firstSet));
		assertTrue(combined.containsAll(secondSet));
		assertEquals(asList(combined.toArray()).subList(0, 3), asList(firstSet.toArray()));
		assertEquals(asList(combined.toArray()).subList(3, 6), asList(secondSet.toArray()));
		assertEquals(combined, hashedCombined);
	}
	
	@Test
	public void hashSetConstructorWithEnumeration() {
		Enumeration<String> enumeration = Collections.enumeration(asList("a", "b", "b", "c"));
		HashSet<String> hashSet = newHashSet(enumeration);
		assertEquals(3, hashSet.size());
		assertTrue(hashSet.containsAll(asList("a", "b", "c")));
	}
	
	@Test
	public void linkedHashSetConstructorWithEnumeration() {
		Enumeration<String> enumeration = Collections.enumeration(asList("a", "b", "b", "c"));
		LinkedHashSet<String> linkedSet = newLinkedHashSet(enumeration);
		assertEquals(3, linkedSet.size());
		assertTrue(linkedSet.containsAll(asList("a", "b", "c")));
	}
}
