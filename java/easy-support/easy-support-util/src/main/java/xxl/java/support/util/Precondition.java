package xxl.java.support.util;

import java.util.Collection;
import java.util.HashSet;

public class Precondition {

	public static void checkTrue(boolean evaluation, String msg) {
		if (!evaluation) {
			throw new IllegalStateException(msg);
		}
	}

	public static void checkFalse(boolean evaluation, String msg) {
		if (evaluation) {
			throw new IllegalStateException(msg);
		}
	}

	public static void checkNotNull(Object object) {
		if (object == null) {
			throw new IllegalArgumentException("Object should not be null");
		}
	}

	public static void checkNull(Object object) {
		if (object != null) {
			throw new IllegalArgumentException("Object should be null");
		}
	}

	public static <T> void checkSameSize(Collection<? extends T> one, Collection<? extends T> other) {
		if (one.size() != other.size()) {
			throw new IllegalStateException("Collections of different size");
		}
	}

	public static <T> void checkSameArraySize(T[] one, T[] other) {
		if (one.length != other.length) {
			throw new IllegalStateException("Arrays of different size");
		}
	}

	public static <T> void checkContainsAll(Collection<? extends T> collection, Collection<? extends T> shouldContain) {
		if (!collection.containsAll(shouldContain)) {
			throw new IllegalStateException("Missing elements in collection");
		}
	}

	/**
	 * It checks that {@code element} appears the same number of times in {@code one} and
	 * {@code other} collections
	 */
	public static <T> void checkEquallyRepeated(T element, Collection<? extends T> one, Collection<? extends T> other) {
		int inOne = repetitions(element, one);
		int inOther = repetitions(element, other);
		if (inOne != inOther) {
			throw new IllegalStateException("Different number of repetitions");
		}
	}

	private static <T> int repetitions(T element, Collection<? extends T> collection) {
		int count = 0;
		boolean isNull = element == null;
		for (T item : collection) {
			if ((isNull && item == null) || (!isNull && element.equals(item))) {
				count += 1;
			}
		}
		return count;
	}

	/**
	 * It checks whether {@code one} and {@code other} have the same size, the same elements,
	 * and the same number of repetitions of each element
	 */
	public static <T> void checkEquals(Collection<? extends T> one, Collection<? extends T> other) {
		checkSameSize(one, other);
		checkContainsAll(one, other);
		Collection<T> set = new HashSet<T>(one);
		for (T element : set) {
			checkEquallyRepeated(element, one, other);
		}
	}

	/**
	 * It checks whether {@code one} and {@code other} have the same size, the same elements,
	 * and in the same order
	 */
	public static <T> void checkArrayEquals(T[] one, T[] other) {
		checkSameArraySize(one, other);
		for (int i = 0; i < one.length; i += 1) {
			checkTrue(one[i].equals(other[i]), "Arrays with different elements");
		}
	}
}
