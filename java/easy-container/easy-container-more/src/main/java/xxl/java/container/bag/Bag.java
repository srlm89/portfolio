package xxl.java.container.bag;

import static java.lang.Math.min;
import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.*;

public class Bag<T> {

	public static <T> Bag<T> empty() {
		return NullBag.instance();
	}

	public static <T extends Comparable<T>> T accessedInOrder(int elementNumber, Bag<T> bag) {
		if ((elementNumber < 0) || (elementNumber >= bag.size())) {
			String format = "Tried to access a bag in order with invalid index (%d, size: %d)";
			throw new IndexOutOfBoundsException(format(format, elementNumber, bag.size()));
		}
		T element = null;
		for (T key : sortedKeys(bag)) {
			elementNumber -= bag.repetitionsOf(key);
			if (elementNumber < 0) {
				element = key;
				break;
			}
		}
		return element;
	}

	public static <T extends Comparable<T>> List<T> sortedKeys(Bag<T> bag) {
		List<T> sorted = new ArrayList<T>(bag.asSet());
		Collections.sort(sorted);
		return sorted;
	}

	public static <T> Bag<T> newHashBag() {
		Map<T, Integer> newMap = new HashMap<T, Integer>();
		return new Bag<T>(newMap);
	}

	@SafeVarargs
	public static <T> Bag<T> newHashBag(T... elements) {
		return newHashBag(asList(elements));
	}

	public static <T> Bag<T> newHashBag(Collection<T> elements) {
		Bag<T> newBag = newHashBag();
		newBag.addAll(elements);
		return newBag;
	}

	public static <T> Bag<T> newHashBag(List<T> elements, List<Integer> repetitions) {
		Bag<T> newBag = newHashBag();
		newBag.putAll(elements, repetitions);
		return newBag;
	}

	public static <T> Bag<T> flatBag(Collection<Bag<T>> bags) {
		Bag<T> flatBag = newHashBag();
		for (Bag<T> bag : bags) {
			flatBag.addAll(bag);
		}
		return flatBag;
	}

	protected Bag(Map<T, Integer> emptyMap) {
		size = 0;
		frequencyMap = emptyMap;
	}

	public boolean contains(T element) {
		return repetitionsOf(element) > 0;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public void clear() {
		setSize(0);
		asFrequencyMap().clear();
	}

	public int size() {
		return size;
	}

	public void putAll(List<T> elements, List<Integer> repetitions) {
		int elementsSize = elements.size();
		if (elementsSize == repetitions.size()) {
			for (int i = 0; i < elementsSize; i += 1) {
				add(elements.get(i), repetitions.get(i));
			}
		}
	}

	public boolean addAll(Collection<? extends T> elements) {
		for (T element : elements) {
			add(element);
		}
		return true;
	}

	public void addAll(Bag<T> aBag) {
		Map<T, Integer> frequencyMap = aBag.asFrequencyMap();
		for (T element : frequencyMap.keySet()) {
			add(element, frequencyMap.get(element));
		}
	}

	public int add(T element) {
		return add(element, 1);
	}

	public int add(T object, int numberOfTimes) {
		int oldValue = repetitionsOf(object);
		if (numberOfTimes > 0) {
			setSize(size() + numberOfTimes);
			asFrequencyMap().put(object, oldValue + numberOfTimes);
		}
		return oldValue;
	}

	public boolean remove(Bag<T> bag) {
		int originalSize = size();
		for (T key : bag.asSet()) {
			remove(key, bag.repetitionsOf(key));
		}
		return originalSize > size();
	}

	public boolean remove(T element) {
		return remove(element, 1);
	}

	public boolean remove(T object, int numberOfTimes) {
		int repetitions = repetitionsOf(object);
		if (repetitions > 0 && numberOfTimes > 0) {
			numberOfTimes = min(repetitions, numberOfTimes);
			asFrequencyMap().put(object, repetitions - numberOfTimes);
			if (repetitions == numberOfTimes) {
				asFrequencyMap().remove(object);
			}
			setSize(size() - numberOfTimes);
			return true;
		}
		return false;
	}

	public int repetitionsOf(T element) {
		if (asFrequencyMap().containsKey(element)) {
			return asFrequencyMap().get(element);
		}
		return 0;
	}

	public Bag<T> snapshot() {
		if (isEmpty()) {
			return empty();
		}
		return copy();
	}

	public Bag<T> copy() {
		Bag<T> newBag = newHashBag();
		newBag.addAll(this);
		return newBag;
	}

	public Set<T> asSet() {
		return asFrequencyMap().keySet();
	}

	public Map<T, Integer> asFrequencyMap() {
		return frequencyMap;
	}

	private void setSize(int value) {
		size = value;
	}

	@Override
	public int hashCode() {
		return asFrequencyMap().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		Bag<?> other = (Bag<?>) object;
		return asFrequencyMap().equals(other.asFrequencyMap());
	}

	@Override
	public String toString() {
		return asFrequencyMap().toString();
	}

	private int size;
	private Map<T, Integer> frequencyMap;
}
