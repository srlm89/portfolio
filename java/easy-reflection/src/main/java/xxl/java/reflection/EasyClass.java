package xxl.java.reflection;

import static java.lang.String.format;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

public class EasyClass {

	public static <T> T newInstance(Class<T> theClass) {
		try {
			return theClass.newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Colud not instantiate " + theClass, e);
		}
	}

	public static Object invoke(Method method, Object receiver, Object... arguments) {
		try {
			return method.invoke(receiver, arguments);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(format("Failed invocation to %s", method.toString()), e);
		}
	}

	public static Object invokeTrespassing(Method method, Object receiver, Object... arguments) {
		boolean oldValue = method.isAccessible();
		method.setAccessible(true);
		try {
			Object response = invoke(method, receiver, arguments);
			return response;
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			method.setAccessible(oldValue);
		}
	}

	public static boolean hasMethod(Class<?> aClass, String methodName, Class<?>... argumentClasses) {
		try {
			return method(methodName, aClass, argumentClasses) != null;
		}
		catch (Exception e) {
			return false;
		}
	}

	public static Method method(String methodName, Class<?> aClass, Class<?>... argumentClasses) {
		Class<?> queriedClass = aClass;
		while (queriedClass != null) {
			try {
				return queriedClass.getDeclaredMethod(methodName, argumentClasses);
			}
			catch (NoSuchMethodException e) {
				e.getLocalizedMessage();
			}
			catch (SecurityException e) {
				e.getLocalizedMessage();
			}
			queriedClass = queriedClass.getSuperclass();
		}
		throw new RuntimeException(format("Method not found %s#%s", aClass.getName(), methodName));
	}

	public static Collection<Class<?>> asClasses(Collection<? extends Object> objects) {
		Collection<Class<?>> classes = new ArrayList<Class<?>>(objects.size());
		for (Object object : objects) {
			classes.add(asClass(object));
		}
		return classes;
	}

	public static Class<?> asClass(Object object) {
		if (Class.class.isInstance(object)) {
			return Class.class.cast(object);
		}
		return object.getClass();
	}

	public static boolean isInstanceOf(Class<?> aClass, Object object) {
		return aClass.isInstance(object);
	}

	public static boolean isSuperclassOf(Class<?> subclass, Class<?> thisClass) {
		return thisClass.isAssignableFrom(subclass);
	}

	public static boolean isSubclassOf(Class<?> superclass, Class<?> thisClass) {
		/** "is assignable from" ~= "is superclass of" */
		return superclass.isAssignableFrom(thisClass);
	}

	public static boolean isAbstract(Class<?> aClass) {
		return Modifier.isAbstract(aClass.getModifiers());
	}

	public static <T> boolean isGreaterThan(T compared, T inRelationTo) {
		return comparison(compared, inRelationTo) > 0;
	}

	public static <T> boolean isLessThan(T compared, T inRelationTo) {
		return comparison(compared, inRelationTo) < 0;
	}

	@SuppressWarnings("unchecked")
	public static <T> int comparison(T one, T other) {
		if (isInstanceOf(Comparable.class, one)) {
			return ((Comparable<T>) one).compareTo(other);
		}
		return 0;
	}
}
