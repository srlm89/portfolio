package xxl.java.reflection.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.Result;

public class TestSuiteExecutionTest {

	@Test
	public void runSuite() {
		Result result = TestSuiteExecution.runCasesIn(new String[] { sampleTestClass() }, classLoaderWithTestClass());
		assertFalse(result.wasSuccessful());
		assertTrue(3 == result.getRunCount());
		assertTrue(1 == result.getFailureCount());
	}

	@Test
	public void runSingleTest() {
		TestCase testCase = TestCase.from(sampleTestClass(), "joinTrue", 1);
		Result result = TestSuiteExecution.runTestCase(testCase, classLoaderWithTestClass());
		assertTrue(result.wasSuccessful());
		assertEquals(1, result.getRunCount());
		assertEquals(0, result.getFailureCount());
	}

	@Test
	public void runSuiteWithTestListener() {
		TestCasesListener listener = new TestCasesListener();
		TestSuiteExecution.runCasesIn(new String[] { sampleTestClass() }, classLoaderWithTestClass(), listener);
		assertEquals(3, listener.allTests().size());
		assertEquals(2, listener.successfulTests().size());
		assertEquals(1, listener.failedTests().size());
	}

	@Test
	public void doNotUseSameTestNameTwice() {
		TestCasesListener listener = new TestCasesListener();
		TestSuiteExecution.runCasesIn(new String[] { sampleTestClass(), sampleTestClass() }, classLoaderWithTestClass(),
				listener);
		assertEquals(6, listener.allTests().size());
		assertEquals(4, listener.successfulTests().size());
		assertEquals(2, listener.failedTests().size());
	}

	@Test
	public void compoundResultForMultipleTestCases() {
		TestCasesListener listener = new TestCasesListener();
		TestSuiteExecution.runCasesIn(new String[] { sampleTestClass() }, classLoaderWithTestClass(), listener);
		Collection<TestCase> failedTests = listener.failedTests();
		assertFalse(failedTests.isEmpty());
		Collection<TestCase> successfulTests = listener.successfulTests();
		assertFalse(successfulTests.isEmpty());
		CompoundResult compound;

		compound = TestSuiteExecution.runTestCases(failedTests, classLoaderWithTestClass());
		assertFalse(compound.wasSuccessful());
		assertTrue(failedTests.size() == compound.getFailureCount());
		assertTrue(failedTests.size() == compound.getRunCount());
		assertTrue(0 == compound.getIgnoreCount());
		assertTrue(compound.successes().isEmpty());

		compound = TestSuiteExecution.runTestCases(successfulTests, classLoaderWithTestClass());
		assertTrue(compound.wasSuccessful());
		assertTrue(0 == compound.getFailureCount());
		assertTrue(successfulTests.size() == compound.getRunCount());
		assertTrue(0 == compound.getIgnoreCount());
		assertTrue(compound.failures().isEmpty());
	}

	@Test
	public void runJUnit3Tests() {
		TestCasesListener listener = new TestCasesListener();
		TestSuiteExecution.runCasesIn(new String[] { sampleTestCase() }, classLoaderWithTestCase(), listener);
		Collection<TestCase> cases = listener.allTests();
		assertEquals(3, cases.size());
	}

	private ClassLoader classLoaderWithTestClass() {
		URL jar = getClass().getResource("TestClass.jar");
		assertFalse(jar == null);
		return new URLClassLoader(new URL[] { jar });
	}

	private ClassLoader classLoaderWithTestCase() {
		URL jar = getClass().getResource("SampleTestCase.jar");
		assertFalse(jar == null);
		return new URLClassLoader(new URL[] { jar });
	}

	private String sampleTestClass() {
		return "xxl.java.junit.sample.TestClass";
	}

	private String sampleTestCase() {
		return "xxl.java.junit.SampleTestCase";
	}
}
