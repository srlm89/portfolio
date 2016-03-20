package xxl.java.container.map;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

public class Multimap<K, V> extends NeoMap<K, Collection<V>> {

	public static interface CollectionFactory<V> extends ValueFactory<Collection<? extends V>> {

		Collection<? extends V> newInstance();
	}

	public static class LinkedListFactory<V> implements CollectionFactory<V> {
		@Override
		public Collection<V> newInstance() {
			return new LinkedList<V>();
		}
	}

	public static class ArrayListFactory<V> implements CollectionFactory<V> {
		@Override
		public Collection<V> newInstance() {
			return new ArrayList<V>();
		}
	}

	public static class HashSetFactory<V> implements CollectionFactory<V> {
		@Override
		public Collection<V> newInstance() {
			return new HashSet<V>();
		}
	}

	public static class LinkedHashSetFactory<V> implements CollectionFactory<V> {
		@Override
		public Collection<V> newInstance() {
			return new LinkedHashSet<V>();
		}
	}

	public static <K, V> Multimap<K, V> newListMultimap() {
		return new Multimap<K, V>(new HashMap<K, Collection<V>>(), new LinkedListFactory<V>());
	}

	public static <K, V> Multimap<K, V> newSetMultimap() {
		return new Multimap<K, V>(new HashMap<K, Collection<V>>(), new HashSetFactory<V>());
	}

	public static <K, V> Multimap<K, V> newLinkedHashSetMultimap() {
		return new Multimap<K, V>(new HashMap<K, Collection<V>>(), new LinkedHashSetFactory<V>());
	}

	public static <K, V> Multimap<K, V> newListOrderedMultimap() {
		return new Multimap<K, V>(new LinkedHashMap<K, Collection<V>>(), new LinkedListFactory<V>());
	}

	public static <K, V> Multimap<K, V> newSetOrderedMultimap() {
		return new Multimap<K, V>(new LinkedHashMap<K, Collection<V>>(), new HashSetFactory<V>());
	}

	public static <K, V> Multimap<K, V> newLinkedHashSetOrderedMultimap() {
		return new Multimap<K, V>(new LinkedHashMap<K, Collection<V>>(), new LinkedHashSetFactory<V>());
	}

	public static <K, V> Multimap<K, V> newIdentityHashListMultimap(int keyCapacity) {
		return new Multimap<K, V>(new IdentityHashMap<K, Collection<V>>(), new LinkedListFactory<V>());
	}

	public static <K, V> Multimap<K, V> newIdentityHashSetMultimap(int keyCapacity) {
		return new Multimap<K, V>(new IdentityHashMap<K, Collection<V>>(), new HashSetFactory<V>());
	}

	public static <K, V> Multimap<K, V> newIdentityLinkedHashSetMultimap(int keyCapacity) {
		return new Multimap<K, V>(new IdentityHashMap<K, Collection<V>>(), new LinkedHashSetFactory<V>());
	}

	private Multimap(Map<K, Collection<V>> subject, CollectionFactory<V> factory) {
		super(subject);
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	public void addAll(K key, V... values) {
		addAll(key, asList(values));
	}

	public void addAll(K key, Collection<V> values) {
		for (V value : values) {
			add(key, value);
		}
	}

	public void addAll(Map<K, V> map) {
		for (Entry<K, V> entry : map.entrySet()) {
			add(entry.getKey(), entry.getValue());
		}
	}

	public void addAll(Collection<Map<K, V>> maps) {
		for (Map<K, V> map : maps) {
			addAll(map);
		}
	}

	public boolean add(K key, V value) {
		return getPutIfAbsent(key, factory()).add(value);
	}

	public int totalValuesOf(K key) {
		return getIfAbsent(key, factory()).size();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ValueFactory<? extends Collection<V>> factory() {
		return (ValueFactory) factory;
	}

	private CollectionFactory<? extends V> factory;
}
