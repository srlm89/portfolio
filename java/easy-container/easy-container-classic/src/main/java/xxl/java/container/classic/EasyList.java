package xxl.java.container.classic;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class EasyList {

	public static interface ListFactory<T> {

		List<T> newInstance();
	}

	/** Factory */

	public static <T> ListFactory<T> arrayListFactory() {
		return new ListFactory<T>() {
			@Override
			public List<T> newInstance() {
				return newArrayList();
			}
		};
	}

	public static <T> ListFactory<T> linkedListFactory() {
		return new ListFactory<T>() {
			@Override
			public List<T> newInstance() {
				return newLinkedList();
			}
		};
	}

	/** ArrayList */

	public static <T> ArrayList<T> newArrayList() {
		return newArrayList(1);
	}

	public static <T> ArrayList<T> newArrayList(int initialCapacity) {
		return new ArrayList<T>((initialCapacity < 0) ? 1 : initialCapacity);
	}

	public static <T> ArrayList<T> newArrayList(T element, int repetitions) {
		ArrayList<T> newList = newArrayList(repetitions);
		return (ArrayList<T>) EasyCollection.withMany(newList, element, repetitions);
	}

	@SafeVarargs
	public static <T> ArrayList<T> newArrayList(T... elements) {
		return newArrayList(asList(elements));
	}

	public static <T> ArrayList<T> newArrayList(Collection<? extends T> collection) {
		ArrayList<T> newList = newArrayList(collection.size());
		return (ArrayList<T>) EasyCollection.withAll(newList, collection);
	}

	public static <T> ArrayList<T> newArrayList(Enumeration<? extends T> enumeration) {
		ArrayList<T> newList = newArrayList();
		return (ArrayList<T>) EasyCollection.withAll(newList, enumeration);
	}

	@SafeVarargs
	public static <T> ArrayList<T> flatArrayList(Collection<? extends T>... collections) {
		ArrayList<T> newList = newArrayList(EasyCollection.combinedSize(collections));
		return (ArrayList<T>) EasyCollection.withAllFlat(newList, collections);
	}

	/** LinkedList */

	public static <T> LinkedList<T> newLinkedList() {
		return new LinkedList<T>();
	}

	public static <T> LinkedList<T> newLinkedList(T element, int repetitions) {
		LinkedList<T> newList = newLinkedList();
		return (LinkedList<T>) EasyCollection.withMany(newList, element, repetitions);
	}

	@SafeVarargs
	public static <T> LinkedList<T> newLinkedList(T... elements) {
		return newLinkedList(asList(elements));
	}

	public static <T> LinkedList<T> newLinkedList(Collection<? extends T> collection) {
		LinkedList<T> newList = newLinkedList();
		return (LinkedList<T>) EasyCollection.withAll(newList, collection);
	}

	public static <T> LinkedList<T> newLinkedList(Enumeration<? extends T> enumeration) {
		LinkedList<T> newList = newLinkedList();
		return (LinkedList<T>) EasyCollection.withAll(newList, enumeration);
	}

	@SafeVarargs
	public static <T> LinkedList<T> flatLinkedList(Collection<? extends T>... collections) {
		LinkedList<T> newList = newLinkedList();
		return (LinkedList<T>) EasyCollection.withAllFlat(newList, collections);
	}

	/** Operations */

	public static <T> T head(List<T> list) {
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public static <T> List<T> head(int numberOfElements, List<T> list) {
		if (!list.isEmpty()) {
			return list.subList(0, Math.min(list.size(), Math.max(0, numberOfElements)));
		}
		return (List<T>) EasyCollection.copyOf(list);
	}

	public static <T> List<T> tail(List<T> list) {
		if (!list.isEmpty()) {
			return tail(list.size() - 1, list);
		}
		return null;
	}

	public static <T> List<T> tail(int numberOfElements, List<T> list) {
		if (!list.isEmpty()) {
			int length = list.size();
			return list.subList(Math.min(length, Math.max(0, length - numberOfElements)), length);
		}
		return (List<T>) EasyCollection.copyOf(list);
	}

	public static <T> T last(List<T> list) {
		if (!list.isEmpty()) {
			return list.get(list.size() - 1);
		}
		return null;
	}

	@SafeVarargs
	public static <T> boolean isPartitionOf(List<T> queriedList, List<? extends T>... partition) {
		boolean sameSize = queriedList.size() == EasyCollection.combinedSize(partition);
		if (sameSize) {
			int startIndex = 0;
			for (List<? extends T> subPartition : partition) {
				List<T> subList = queriedList.subList(startIndex, startIndex + subPartition.size());
				if (!subList.equals(subPartition)) {
					return false;
				}
				startIndex += subPartition.size();
			}
		}
		return sameSize;
	}
}
