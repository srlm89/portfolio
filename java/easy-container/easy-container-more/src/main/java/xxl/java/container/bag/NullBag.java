package xxl.java.container.bag;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class NullBag<T> extends Bag<T> {

	@SuppressWarnings("unchecked")
	protected static <T> NullBag<T> instance() {
		if (instance == null) {
			Map<T, Integer> empty = new HashMap<T, Integer>();
			instance = new NullBag(empty);
		}
		return instance;
	}

	@Override
	public int add(T object, int numberOfTimes) {
		return 0;
	}

	private NullBag(Map<T, Integer> emptyMap) {
		super(emptyMap);
	}

	private static NullBag instance;
}
