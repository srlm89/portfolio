package xxl.java.support.util;

import java.util.Collection;

public interface Observable<T> {

	void register(Observer<T> observer);
	
	void unregister(Observer<T> observer);
	
	void notifyObservers(T observable);
	
	Collection<Observer<T>> observers();
}
