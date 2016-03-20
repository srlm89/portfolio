package xxl.java.container.classic;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EasyMap {

	public static interface Function<K, V> {

		V outputFor(K key);
	}

	public static interface MapFactory<K, V> {

		Map<K, V> newInstance();
	}

	/** Method */

	public static <K, V> Function<K, V> methodGet(final Map<K, V> map) {
		return new Function<K, V>() {
			@Override
			public V outputFor(K key) {
				return map.get(key);
			}
		};
	}

	/** Factory */

	public static <K, V> MapFactory<K, V> hashMapFactory() {
		return new MapFactory<K, V>() {
			@Override
			public Map<K, V> newInstance() {
				return newHashMap();
			}
		};
	}

	public static <K, V> MapFactory<K, V> linkedHashMapFactory() {
		return new MapFactory<K, V>() {
			@Override
			public Map<K, V> newInstance() {
				return newLinkedHashMap();
			}
		};
	}

	/** HashMap */

	public static <K, V> HashMap<K, V> newHashMap() {
		return newHashMap(1);
	}

	public static <K, V> HashMap<K, V> newHashMap(int initialCapacity) {
		return new HashMap<K, V>((initialCapacity < 0) ? 1 : initialCapacity);
	}

	public static <K, V> HashMap<K, V> newHashMap(Map<K, V> baseMap) {
		HashMap<K, V> newMap = newHashMap(baseMap.size());
		return (HashMap<K, V>) withAll(newMap, baseMap);
	}

	public static <K, V> HashMap<K, V> newHashMap(K key, V value) {
		return newHashMap(value, asList(key));
	}

	public static <K, V> HashMap<K, V> newHashMap(V value, Collection<K> keys) {
		HashMap<K, V> newMap = newHashMap(keys.size());
		return (HashMap<K, V>) withMany(newMap, value, keys);
	}

	public static <K, V> HashMap<K, V> newHashMap(List<K> keys, List<V> values) {
		HashMap<K, V> newMap = newHashMap(keys.size());
		return (HashMap<K, V>) withAll(newMap, keys, values);
	}

	public static <K, V> HashMap<K, V> newHashMap(Collection<K> keys, Function<K, V> toValue) {
		HashMap<K, V> newMap = newHashMap(keys.size());
		return (HashMap<K, V>) withAll(newMap, keys, toValue);
	}

	/** LinkedHashMap */

	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
		return newLinkedHashMap(1);
	}

	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(int initialCapacity) {
		return new LinkedHashMap<K, V>((initialCapacity < 0) ? 1 : initialCapacity);
	}

	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<K, V> baseMap) {
		LinkedHashMap<K, V> newMap = newLinkedHashMap(baseMap.size());
		return (LinkedHashMap<K, V>) withAll(newMap, baseMap);
	}

	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(K key, V value) {
		return newLinkedHashMap(value, asList(key));
	}

	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(V value, Collection<K> keys) {
		LinkedHashMap<K, V> newMap = newLinkedHashMap(keys.size());
		return (LinkedHashMap<K, V>) withMany(newMap, value, keys);
	}

	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(List<K> keys, List<V> values) {
		LinkedHashMap<K, V> newMap = newLinkedHashMap(keys.size());
		return (LinkedHashMap<K, V>) withAll(newMap, keys, values);
	}

	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Collection<K> keys, Function<K, V> toValue) {
		LinkedHashMap<K, V> newMap = newLinkedHashMap(keys.size());
		return (LinkedHashMap<K, V>) withAll(newMap, keys, toValue);
	}

	/** IdentityHashMap */

	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int keyCapacity) {
		return new IdentityHashMap<K, V>(keyCapacity);
	}

	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int keyCapacity, Map<K, V> baseMap) {
		IdentityHashMap<K, V> newMap = newIdentityHashMap(keyCapacity);
		return (IdentityHashMap<K, V>) withAll(newMap, baseMap);
	}

	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int keyCapacity, K key, V value) {
		return newIdentityHashMap(keyCapacity, value, asList(key));
	}

	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int keyCapacity, V value, Collection<K> keys) {
		IdentityHashMap<K, V> newMap = newIdentityHashMap(keyCapacity);
		return (IdentityHashMap<K, V>) withMany(newMap, value, keys);
	}

	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int keyCapacity, List<K> keys, List<V> values) {
		IdentityHashMap<K, V> newMap = newIdentityHashMap(keyCapacity);
		return (IdentityHashMap<K, V>) withAll(newMap, keys, values);
	}

	public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(int keyCapacity, Collection<K> keys, Function<K, V> toValue) {
		IdentityHashMap<K, V> newMap = newIdentityHashMap(keyCapacity);
		return (IdentityHashMap<K, V>) withAll(newMap, keys, toValue);
	}

	/** Operations */

	public static <K, V> Map<K, V> withMany(Map<K, V> destination, V value, Collection<K> keys) {
		for (K key : keys) {
			destination.put(key, value);
		}
		return destination;
	}

	public static <K, V> Map<K, V> withAll(Map<K, V> destination, Map<K, V> sourceMap) {
		destination.putAll(sourceMap);
		return destination;
	}

	public static <K, V> Map<K, V> withAll(Map<K, V> destination, List<K> keys, List<V> values) {
		putAll(destination, keys, values);
		return destination;
	}

	public static <K, V> Map<K, V> withAll(Map<K, V> destination, Collection<K> keys, Function<K, V> toValue) {
		putAll(destination, keys, toValue);
		return destination;
	}

	public static <K, V> Map<K, V> withAllFlat(Map<K, V> destination, Collection<Map<K, V>> maps) {
		putAllFlat(destination, maps);
		return destination;
	}

	public static <K, V> Map<K, V> putMany(Map<K, V> sourceMap, V value, Collection<K> keys) {
		Map<K, V> previousValues = newHashMap();
		for (K key : keys) {
			V previousValue = sourceMap.put(key, value);
			if (previousValue != null) {
				previousValues.put(key, previousValue);
			}
		}
		return previousValues;
	}

	public static <K, V> Map<K, V> putAll(Map<K, V> sourceMap, Map<K, V> destinationMap) {
		Map<K, V> previousValues = newHashMap();
		for (K key : sourceMap.keySet()) {
			V previousValue = destinationMap.put(key, sourceMap.get(key));
			if (previousValue != null) {
				previousValues.put(key, previousValue);
			}
		}
		return previousValues;
	}

	public static <K, V> Map<K, V> putAll(Map<K, V> map, List<K> keys, List<V> values) {
		Map<K, V> previousValues = newHashMap();
		if (keys.size() == values.size()) {
			int index = 0;
			for (K key : keys) {
				V oldValue = map.put(key, values.get(index));
				if (oldValue != null) {
					previousValues.put(key, oldValue);
				}
				index += 1;
			}
		}
		return previousValues;
	}

	public static <K, V> Map<K, V> putAll(Map<K, V> map, Collection<K> keys, Function<K, V> toValue) {
		Map<K, V> previousValues = newHashMap();
		for (K key : keys) {
			V previousValue = map.put(key, toValue.outputFor(key));
			if (previousValue != null) {
				previousValues.put(key, previousValue);
			}
		}
		return previousValues;
	}

	public static <K, V> void putAllFlat(Map<K, V> newMap, Collection<? extends Map<K, V>> desintationMaps) {
		for (Map<K, V> map : desintationMaps) {
			putAll(newMap, map);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K, V> Map<K, V> newMapInstance(Class<? extends Map> mapClass) {
		try {
			return mapClass.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <K, V> Map<K, V> copyOf(Map<K, V> map) {
		Map<K, V> copy = newMapInstance(map.getClass());
		copy.putAll(map);
		return copy;
	}

	public static <C, K, V> Map<C, V> remade(Map<K, V> map, Function<K, C> toOtherKey) {
		Map<C, V> remade = newMapInstance(map.getClass());
		for (K key : map.keySet()) {
			remade.put(toOtherKey.outputFor(key), map.get(key));
		}
		return remade;
	}

	public static <K, V, B> Map<K, B> remapped(Map<K, V> map, Function<V, B> toOtherValue) {
		Map<K, B> remapped = newMapInstance(map.getClass());
		for (K key : map.keySet()) {
			remapped.put(key, toOtherValue.outputFor(map.get(key)));
		}
		return remapped;
	}

	public static <K, V> V getPutIfAbsent(Map<K, V> map, K key, V valueIfAbsent) {
		if (!map.containsKey(key)) {
			map.put(key, valueIfAbsent);
		}
		return map.get(key);
	}

	public static <K, V> V getIfAbsent(Map<K, V> map, K key, V valueIfAbsent) {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		return valueIfAbsent;
	}

	public static <K> Map<K, Integer> valuesParsedAsInteger(Map<K, String> sourceMap) {
		Map<K, Integer> parsedMap = newHashMap();
		for (K key : sourceMap.keySet()) {
			Integer parsedValue = Integer.valueOf(sourceMap.get(key));
			parsedMap.put(key, parsedValue);
		}
		return parsedMap;
	}

	public static <K, V> Collection<K> keysWithValue(V value, Map<K, V> map) {
		return keysWithValuesIn(asList(value), map);
	}

	public static <K, V> Collection<K> keysWithValuesIn(Collection<V> values, Map<K, V> map) {
		Collection<K> keys = new HashSet<K>();
		for (K key : map.keySet()) {
			if (values.contains(map.get(key))) {
				keys.add(key);
			}
		}
		return keys;
	}

	public static <K, V> Set<K> keySetUnion(Collection<? extends Map<K, V>> maps) {
		Set<K> keys = new HashSet<K>();
		for (Map<K, V> map : maps) {
			keys.addAll(map.keySet());
		}
		return keys;
	}

	public static <K, V> Set<K> keySetIntersection(Collection<? extends Map<K, V>> maps) {
		Set<K> keys = new HashSet<K>();
		if (maps.size() > 0) {
			Iterator<? extends Map<K, V>> mapIterator = maps.iterator();
			keys.addAll(mapIterator.next().keySet());
			while (mapIterator.hasNext()) {
				keys.retainAll(mapIterator.next().keySet());
			}
		}
		return keys;
	}

	public static <K, V> boolean containsAllKeys(Collection<K> keys, Map<K, V> map) {
		for (K key : keys) {
			if (!map.containsKey(key)) {
				return false;
			}
		}
		return true;
	}

	public static <K, V> boolean sameContent(Map<K, V> map, List<K> keys, List<V> values) {
		boolean sameSize = map.size() == keys.size() && map.size() == values.size();
		if (sameSize) {
			int index = 0;
			for (K key : keys) {
				if (!(map.containsKey(key) && values.get(index).equals(map.get(key)))) {
					return false;
				}
				index += 1;
			}
		}
		return sameSize;
	}

	public static <K, V> boolean onlyValueIs(V value, Map<K, V> map) {
		return allValuesIn(asList(value), map);
	}

	public static <K, V> boolean allValuesIn(Collection<V> restrictedValues, Map<K, V> map) {
		for (K key : map.keySet()) {
			if (!restrictedValues.contains(map.get(key))) {
				return false;
			}
		}
		return !map.isEmpty();
	}

	public static <K> Map<K, Integer> frequencies(Collection<K> collection) {
		Map<K, Integer> frequencies = newHashMap();
		for (K element : collection) {
			int count = getPutIfAbsent(frequencies, element, 0);
			frequencies.put(element, count + 1);
		}
		return frequencies;
	}

	public static <K, V> Map<K, V> extractedWithKeys(Collection<K> keys, Map<K, V> map) {
		Map<K, V> extracted = newHashMap();
		for (K key : keys) {
			if (map.containsKey(key)) {
				extracted.put(key, map.get(key));
			}
		}
		return extracted;
	}

	public static <K> Map<K, K> autoMap(Collection<K> keys) {
		Map<K, K> autoMap = newHashMap(keys.size());
		for (K key : keys) {
			autoMap.put(key, key);
		}
		return autoMap;
	}

	public static <K, V> Collection<K> removeKeys(Collection<K> keys, Map<K, V> map) {
		Collection<K> removedKeys = new HashSet<K>();
		for (K key : keys) {
			if (map.containsKey(key)) {
				map.remove(key);
				removedKeys.add(key);
			}
		}
		return removedKeys;
	}

	public static <K, V> void removeKeysInAll(Collection<K> keys, Collection<Map<K, V>> maps) {
		for (Map<K, V> map : maps) {
			removeKeys(keys, map);
		}
	}

	public static <K, V> Map<String, String> asStringMap(Map<K, V> sourceMap) {
		return mapWithStringValues(mapWithStringKeys(sourceMap));
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<String, V> mapWithStringKeys(Map<K, V> sourceMap) {
		return remade(sourceMap, (Function<K, String>) toStringMethod());
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, String> mapWithStringValues(Map<K, V> sourceMap) {
		return remapped(sourceMap, (Function<V, String>) toStringMethod());
	}

	private static <X> Function<X, String> toStringMethod() {
		return new Function<X, String>() {
			@Override
			public String outputFor(X object) {
				return object.toString();
			}
		};
	}
}
