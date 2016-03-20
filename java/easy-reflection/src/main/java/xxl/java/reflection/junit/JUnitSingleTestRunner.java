package xxl.java.reflection.junit;

import java.util.concurrent.Callable;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class JUnitSingleTestRunner implements Callable<Result> {

	public JUnitSingleTestRunner(TestCase testCase, RunListener listener) {
		this.testCase = testCase;
		this.listener = listener;
	}

	@Override
	public Result call() throws Exception {
		JUnitCore runner = new JUnitCore();
		runner.addListener(listener);
		Request request = Request.method(testClassFromCustomClassLoader(), testCaseName());
		return runner.run(request);
	}
	
	private Class<?> testClassFromCustomClassLoader() {
		Class<?> compiledClass; 
		try {
			compiledClass = Thread.currentThread().getContextClassLoader().loadClass(testClassName());
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
		return compiledClass;
	}
	
	public String testCaseName() {
		return testCase.testName();
	}
	
	public String testClassName() {
		return testCase.className();
	}

	private TestCase testCase;
	private RunListener listener;
}
