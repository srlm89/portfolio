package xxl.java.reflection.junit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestSuiteExecution {

	public static Result runCasesIn(String[] testClasses, ClassLoader classLoaderForTestThread) {
		return runCasesIn(testClasses, classLoaderForTestThread, nullRunListener());
	}

	public static Result runCasesIn(String[] testClasses, ClassLoader classLoaderForTestThread, RunListener listener) {
		return executionResult(new JUnitRunner(testClasses, listener), classLoaderForTestThread);
	}

	public static Result runTestCase(TestCase testCase, ClassLoader classLoaderForTestThread) {
		return runTestCase(testCase, classLoaderForTestThread, nullRunListener());
	}

	public static Result runTestCase(TestCase testCase, ClassLoader classLoaderForTestThread, RunListener listener) {
		return executionResult(new JUnitSingleTestRunner(testCase, listener), classLoaderForTestThread);
	}

	public static CompoundResult runTestCases(Collection<TestCase> testCases, ClassLoader classLoaderForTestThread) {
		return runTestCases(testCases, classLoaderForTestThread, nullRunListener());
	}

	public static CompoundResult runTestCases(Collection<TestCase> testCases, ClassLoader classLoaderForTestThread,
			RunListener listener) {
		List<Result> results = new ArrayList<Result>(testCases.size());
		for (TestCase testCase : testCases) {
			results.add(runTestCase(testCase, classLoaderForTestThread, listener));
		}
		return new CompoundResult(results);
	}

	private static Result executionResult(Callable<Result> callable, ClassLoader classLoaderForTestThread) {
		ExecutorService executor = Executors.newSingleThreadExecutor(new CustomClassLoaderThreadFactory(classLoaderForTestThread));
		Result result = null;
		try {
			result = executor.submit(callable).get(secondsForTimeout(), TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		catch (TimeoutException e) {
			log(format("Timeout after %d seconds. Infinite loop?", secondsForTimeout()));
			throw new RuntimeException(e);
		}
		executor.shutdownNow();
		return result;
	}

	public static List<Description> collectDescription(List<Failure> failures) {
		List<Description> descriptions = new LinkedList<Description>();
		for (Failure failure : failures) {
			descriptions.add(failure.getDescription());
		}
		return descriptions;
	}

	protected static long secondsForTimeout() {
		return secondsForTimeout;
	}

	private static RunListener nullRunListener() {
		return NullRunListener.instance();
	}

	private static void log(String message) {
		System.err.println(message);
	}

	private static long secondsForTimeout = MINUTES.toSeconds(60L);
}
