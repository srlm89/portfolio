package xxl.java.container.classic;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class EasySet {

	public static interface SetFactory<T> {

		Set<T> newInstance();
	}

	/** Factory */

	public static <T> SetFactory<T> hashSetFactory() {
		return new SetFactory<T>() {
			@Override
			public Set<T> newInstance() {
				return newHashSet();
			}
		};
	}

	public static <T> SetFactory<T> linkedHashSetFactory() {
		return new SetFactory<T>() {
			@Override
			public Set<T> newInstance() {
				return newLinkedHashSet();
			}
		};
	}

	/** HashSet */

	public static <T> HashSet<T> newHashSet() {
		return newHashSet(1);
	}

	public static <T> HashSet<T> newHashSet(int initialCapacity) {
		return new HashSet<T>((initialCapacity < 0) ? 1 : initialCapacity);
	}

	@SafeVarargs
	public static <T> HashSet<T> newHashSet(T... elements) {
		return newHashSet(asList(elements));
	}

	public static <T> HashSet<T> newHashSet(Collection<? extends T> collection) {
		HashSet<T> newSet = newHashSet(collection.size());
		return (HashSet<T>) EasyCollection.withAll(newSet, collection);
	}

	public static <T> HashSet<T> newHashSet(Enumeration<? extends T> enumeration) {
		HashSet<T> newSet = newHashSet();
		return (HashSet<T>) EasyCollection.withAll(newSet, enumeration);
	}

	@SafeVarargs
	public static <T> HashSet<T> flatHashSet(Collection<? extends T>... collections) {
		HashSet<T> newSet = newHashSet(collections.length);
		return (HashSet<T>) EasyCollection.withAllFlat(newSet, collections);
	}

	/** LinkedHashSet */

	public static <T> LinkedHashSet<T> newLinkedHashSet() {
		return newLinkedHashSet(1);
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet(int initialCapacity) {
		return new LinkedHashSet<T>((initialCapacity < 0) ? 1 : initialCapacity);
	}

	@SafeVarargs
	public static <T> LinkedHashSet<T> newLinkedHashSet(T... elements) {
		return newLinkedHashSet(asList(elements));
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet(Collection<? extends T> collection) {
		LinkedHashSet<T> newSet = newLinkedHashSet(collection.size());
		return (LinkedHashSet<T>) EasyCollection.withAll(newSet, collection);
	}

	public static <T> LinkedHashSet<T> newLinkedHashSet(Enumeration<? extends T> enumeration) {
		LinkedHashSet<T> newSet = newLinkedHashSet();
		return (LinkedHashSet<T>) EasyCollection.withAll(newSet, enumeration);
	}

	@SafeVarargs
	public static <T> LinkedHashSet<T> flatLinkedHashSet(Collection<? extends T>... collections) {
		LinkedHashSet<T> newSet = newLinkedHashSet(collections.length);
		return (LinkedHashSet<T>) EasyCollection.withAllFlat(newSet, collections);
	}
}
