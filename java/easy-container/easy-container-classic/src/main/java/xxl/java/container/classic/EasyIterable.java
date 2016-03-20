package xxl.java.container.classic;

import java.util.Enumeration;
import java.util.Iterator;

public class EasyIterable {

	@SuppressWarnings("unused")
	public static <T> int size(Iterable<? extends T> iterable) {
		int size = 0;
		for (T element : iterable) {
			size += 1;
		}
		return size;
	}
	
	public static <T> Iterable<T> asIterable(final Enumeration<? extends T> enumeration) {
		return new Iterable<T>() {

			@Override
			public Iterator<T> iterator() {
				return iteratorOf(enumeration);
			}
			
		};
	}
	
	public static <T> Iterator<T> iteratorOf(final Enumeration<? extends T> enumeration) {
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public T next() {
				return enumeration.nextElement();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("Cannot remove from an enumeration");
			}
			
		};
	}
}
