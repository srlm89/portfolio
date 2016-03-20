package xxl.java.container.bag;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

public class BagTest {

	@Test
	public void bag() {
		Bag<String> bag = Bag.newHashBag();
		assertTrue(bag.isEmpty());
		bag.add("a");
		assertFalse(bag.isEmpty());
		assertTrue(bag.contains("a"));
		assertEquals(1, bag.repetitionsOf("a"));
		assertEquals(1, bag.size());
		bag.add("b");
		assertTrue(bag.contains("b"));
		assertEquals(1, bag.repetitionsOf("b"));
		assertEquals(2, bag.size());
		bag.add("a");
		assertEquals(2, bag.repetitionsOf("a"));
		assertEquals(3, bag.size());
		bag.remove("b");
		assertFalse(bag.contains("b"));
		assertEquals(0, bag.repetitionsOf("b"));
		assertEquals(2, bag.size());
		bag.remove("c");
		assertEquals(2, bag.size());
	}

	@Test
	public void bagFromCollection() {
		Bag<String> newBag = Bag.newHashBag(asList("a", "a", "b", "c"));
		assertFalse(newBag.isEmpty());
		assertEquals(4, newBag.size());
		assertTrue(newBag.contains("a"));
		assertTrue(newBag.contains("b"));
		assertTrue(newBag.contains("c"));
		assertEquals(2, newBag.repetitionsOf("a"));
		assertEquals(1, newBag.repetitionsOf("b"));
		assertEquals(1, newBag.repetitionsOf("c"));
	}

	@Test
	public void bagFromVarargs() {
		Bag<String> newBag = Bag.newHashBag("a", "a", "b", "c");
		assertFalse(newBag.isEmpty());
		assertEquals(4, newBag.size());
		assertTrue(newBag.contains("a"));
		assertTrue(newBag.contains("b"));
		assertTrue(newBag.contains("c"));
		assertEquals(2, newBag.repetitionsOf("a"));
		assertEquals(1, newBag.repetitionsOf("b"));
		assertEquals(1, newBag.repetitionsOf("c"));
	}

	@Test
	public void clearABag() {
		Bag<String> newBag = Bag.newHashBag(asList("a", "a", "b", "c"));
		assertEquals(4, newBag.size());
		newBag.clear();
		assertTrue(newBag.isEmpty());
		assertEquals(0, newBag.size());
		newBag.add("a");
		assertEquals(1, newBag.size());
	}

	@Test
	public void setFromBag() {
		Bag<String> newBag = Bag.newHashBag(asList("a", "b", "c", "b"));
		Set<String> asSet = newBag.asSet();
		assertEquals(3, asSet.size());
		assertTrue(asSet.containsAll(asList("a", "b", "c")));
	}

	@Test
	public void frequencyMapFromBag() {
		Bag<String> newBag = Bag.newHashBag(asList("a", "b", "c", "b"));
		Map<String, Integer> frequencyMap = newBag.asFrequencyMap();
		assertEquals(3, frequencyMap.size());
		assertTrue(frequencyMap.containsKey("a"));
		assertEquals(1, frequencyMap.get("a").intValue());
		assertTrue(frequencyMap.containsKey("b"));
		assertEquals(2, frequencyMap.get("b").intValue());
		assertTrue(frequencyMap.containsKey("c"));
		assertEquals(1, frequencyMap.get("c").intValue());
	}

	@Test
	public void multipleAddToBag() {
		Bag<String> newBag = Bag.newHashBag();
		newBag.add("a");
		assertEquals(1, newBag.repetitionsOf("a"));
		newBag.add("a", 0);
		assertEquals(1, newBag.repetitionsOf("a"));
		newBag.add("a", -2);
		assertEquals(1, newBag.repetitionsOf("a"));
		newBag.add("a", 2);
		assertEquals(3, newBag.repetitionsOf("a"));
	}

	@Test
	public void addFromCollection() {
		Bag<String> newBag = Bag.newHashBag();
		assertEquals(0, newBag.repetitionsOf("a"));
		newBag.addAll(asList("a", "a", "a"));
		assertEquals(3, newBag.repetitionsOf("a"));
	}

	@Test
	public void addFromOtherBag() {
		Bag<String> newBag = Bag.newHashBag();
		assertEquals(0, newBag.repetitionsOf("a"));
		Bag<String> otherBag = Bag.newHashBag(asList("a", "b", "b"));
		newBag.addAll(otherBag);
		assertEquals(3, newBag.size());
		assertEquals(1, newBag.repetitionsOf("a"));
		assertEquals(2, newBag.repetitionsOf("b"));
		assertTrue(newBag.equals(otherBag));
		assertTrue(otherBag.equals(newBag));
		assertTrue(newBag.hashCode() == otherBag.hashCode());
	}

	@Test
	public void multipleRemoveToBag() {
		Bag<String> newBag = Bag.newHashBag(asList("a", "b", "c", "d", "c", "c", "c", "d", "b"));
		assertFalse(newBag.remove("z"));
		assertFalse(newBag.remove("c", 0));
		assertEquals(9, newBag.size());
		assertTrue(newBag.remove("a"));
		assertEquals(8, newBag.size());
		assertEquals(0, newBag.repetitionsOf("a"));
		assertTrue(newBag.remove("c", 10));
		assertEquals(4, newBag.size());
		assertEquals(0, newBag.repetitionsOf("c"));
		assertTrue(newBag.remove("d", 2));
		assertEquals(2, newBag.size());
		assertEquals(0, newBag.repetitionsOf("d"));
		assertTrue(newBag.remove("b"));
		assertEquals(1, newBag.size());
		assertEquals(1, newBag.repetitionsOf("b"));
	}

	@Test
	public void constructorWithLists() {
		List<String> emptyStringList = new ArrayList<String>();
		List<Integer> emptyIntegerList = new ArrayList<Integer>();
		assertTrue(Bag.newHashBag(emptyStringList, emptyIntegerList).isEmpty());
		assertTrue(Bag.newHashBag(asList("a"), emptyIntegerList).isEmpty());
		assertTrue(Bag.newHashBag(emptyStringList, asList(1)).isEmpty());
		Bag<String> bag = Bag.newHashBag(asList("a", "c", "d"), asList(2, 1, 3));
		assertEquals(6, bag.size());
		assertEquals(2, bag.repetitionsOf("a"));
		assertEquals(1, bag.repetitionsOf("c"));
		assertEquals(3, bag.repetitionsOf("d"));
	}

	@Test
	public void multipleAddWithLists() {
		Bag<String> bag = Bag.newHashBag(asList("a", "c", "d"), asList(2, 1, 3));
		Bag<String> otherBag = Bag.newHashBag();
		otherBag.putAll(asList("d", "a", "c"), asList(3, 2, 1));
		assertEquals(bag, otherBag);
	}

	@Test
	public void flatBagConstructor() {
		Bag<String> bag = Bag.newHashBag(asList("a", "b", "c"), asList(6, 5, 2));
		Bag<String> miniBagA = Bag.newHashBag("a", "b", "c");
		Bag<String> miniBagB = Bag.newHashBag("a", "b", "c");
		Bag<String> miniBagC = Bag.newHashBag("b", "b", "b");
		Bag<String> miniBagD = Bag.newHashBag("a", "a", "a", "a");
		assertEquals(bag, Bag.flatBag(asList(miniBagA, miniBagB, miniBagC, miniBagD)));
	}

	@Test
	public void singletonForEmptyBag() {
		Bag<String> emptyStringBag = Bag.empty();
		assertTrue(emptyStringBag.isEmpty());
		Bag<Object> emptyObjectBag = Bag.empty();
		assertTrue(emptyObjectBag.isEmpty());
		Bag<Bag<Integer>> emptyBagBag = Bag.empty();
		assertTrue(emptyBagBag.isEmpty());
	}

	@Test
	public void removeBagFromBag() {
		Bag<Character> otherBag = Bag.newHashBag();
		Bag<Character> bag = Bag.newHashBag('a', 'b', 'a', 'b', 'c');
		bag.remove(otherBag);
		assertTrue(5 == bag.size());
		otherBag.addAll(asList('a', 'c'));
		bag.remove(otherBag);
		assertTrue(3 == bag.size());
		assertFalse(bag.contains('c'));
		assertTrue(1 == bag.repetitionsOf('a'));
		bag.remove(otherBag);
		assertTrue(2 == bag.size());
		assertFalse(bag.contains('a'));
		bag.remove(otherBag);
		assertTrue(2 == bag.size());
		assertFalse(bag.contains('a'));
		otherBag.addAll(asList('b', 'b'));
		bag.remove(otherBag);
		assertTrue(bag.isEmpty());
	}

	@Test
	public void nullBagDoesNotAdd() {
		Bag<Object> bag = Bag.empty();
		assertTrue(bag.isEmpty());
		bag.add(2, 2);
		assertTrue(bag.isEmpty());
		bag.add("string");
		assertTrue(bag.isEmpty());
	}
}
