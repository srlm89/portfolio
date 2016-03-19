package xxl.java.primitive;

import static java.lang.String.format;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EasyObject {

	public static interface Function<X, Y> {

		Y outputFor(X value);
	}

	public static <T> Function<T, String> methodToString() {
		return new Function<T, String>() {
			@Override
			public String outputFor(T value) {
				if (value == null) {
					return "null";
				}
				return value.toString();
			}
		};
	}

	public static <U, T> Function<U, T> methodIdentity(final T object) {
		return new Function<U, T>() {
			@Override
			public T outputFor(U value) {
				return object;
			}
		};
	}

	public static <T> Function<T, T> methodYourself() {
		return new Function<T, T>() {
			@Override
			public T outputFor(T value) {
				return value;
			}
		};
	}

	public static String asString(Object object) {
		return object.getClass().getSimpleName();
	}

	public static String asString(Object object, Map<String, ? extends Object> fields) {
		Map<String, String> stringMap = new LinkedHashMap<String, String>(fields.size());
		for (String name : fields.keySet()) {
			Object value = fields.get(name);
			stringMap.put(name, (value == null) ? "null" : value.toString());
		}
		String fieldString = stringMap.toString().replaceFirst("^[{](.*)[}]$", "[$1]");
		return format("%s %s", asString(object), fieldString);
	}

	public static int hashCodeWith(Object... fields) {
		int prime = 31;
		int result = 1;
		for (Object field : fields) {
			result = prime * result + ((field == null) ? 0 : field.hashCode());
		}
		return result;
	}

	public static <T> boolean equals(T thisObject, List<?> thisFields, Object object, List<?> fields) {
		if (thisObject == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (thisObject.getClass() != object.getClass()) {
			return false;
		}
		if (thisFields.size() != fields.size()) {
			throw new IllegalArgumentException("Cannot finish comparison: missing fields");
		}
		for (int i = 0; i < thisFields.size(); i += 1) {
			Object field = fields.get(i);
			Object thisField = thisFields.get(i);
			if (field == null) {
				if (thisField != null) {
					return false;
				}
			}
			else if (!field.equals(thisField)) {
				return false;
			}
		}
		return true;
	}
}
