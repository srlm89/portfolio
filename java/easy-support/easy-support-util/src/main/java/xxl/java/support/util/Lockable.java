package xxl.java.support.util;

import static java.lang.String.format;

public class Lockable<T> {

	public Lockable(T object) {
		this.object = object;
		this.locked = false;
	}

	public T acquire() throws InterruptedException {
		synchronized (this) {
			if (locked) {
				wait();
			}
			locked = true;
		}
		return object;
	}

	public void release() {
		synchronized (this) {
			if (locked) {
				locked = false;
				notify();
			}
		}
	}

	@Override
	public String toString() {
		return format("%s [object= %s]", Lockable.class.getSimpleName(), object);
	}

	private T object;
	private boolean locked;
}
