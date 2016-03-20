package xxl.java.reflection.junit;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public final class JUnitRunner implements Callable<Result> {

	public JUnitRunner(String[] classes, RunListener listener) {
		this.testClasses = classes;
		this.listener = listener;
	}

	@Override
	public Result call() throws Exception {
		JUnitCore runner = new JUnitCore();
		runner.addListener(listener);
		Class<?>[] testClasses = testClassesFromCustomClassLoader();
		return runner.run(testClasses);
	}

	private Class<?>[] testClassesFromCustomClassLoader() {
		Collection<Class<?>> classes = new LinkedList<Class<?>>();
		for (String className : testClasses) {
			try {
				Class<?> testClass = Thread.currentThread().getContextClassLoader().loadClass(className);
				if (!Modifier.isAbstract(testClass.getModifiers())) {
					classes.add(testClass);
				}
			}
			catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(cnfe);
			}
		}
		return classes.toArray(new Class<?>[classes.size()]);
	}

	private final String[] testClasses;
	private final RunListener listener;
}
