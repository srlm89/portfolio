package xxl.java.reflection;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.reflection.EasyExecution.callingStackFrame;
import static xxl.java.reflection.EasyExecution.currentStackFrame;
import static xxl.java.reflection.EasyExecution.currentStackTally;

import org.junit.Test;

public class EasyExecutionTest {

	@Test
	public void testCurrentStackFrame() {
		StackTraceElement frame = currentStackFrame();
		assertTrue("testCurrentStackFrame".equals(frame.getMethodName()));
		assertEquals(getClass().getName(), frame.getClassName());
	}
	
	@Test
	public void testCallingStackFrame() {
		final StackTraceElement[] frames = new StackTraceElement[] { null, null };
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				frames[0] = callingStackFrame();
				frames[1] = currentStackFrame();
			}
		};
		runnable.run();
		StackTraceElement calling = frames[0];
		StackTraceElement current = frames[1];
		assertEquals("testCallingStackFrame", calling.getMethodName());
		assertEquals(getClass().getName(), calling.getClassName());
		assertEquals("run", current.getMethodName());
		assertEquals(runnable.getClass().getName(), current.getClassName());
	}
	
	@Test
	public void testCallingStackFrameInThread() {
		final StackTraceElement[] elements = { null };
		Thread thread = new Thread() {
			@Override
			public void run() {
				elements[0] = callingStackFrame();
			}
		};
		thread.run();
		String methodName = "testCallingStackFrameInThread";
		String declaringClass = getClass().getName();
		String fileName = getClass().getSimpleName() + ".java";
		assertTrue(methodName.equals(elements[0].getMethodName()));
		assertTrue(declaringClass.equals(elements[0].getClassName()));
		assertTrue(fileName.equals(elements[0].getFileName()));
	}
	
	@Test
	public void sameFrameDifferentInvocation() {
		final StackTraceElement[] array = { null, null };
		final int[] index = { 0 };
		Thread thread = new Thread() {
			@Override
			public void run() {
				array[index[0]++] = currentStackFrame();
			}
		};
		thread.run();
		thread.run();
		String methodName = "run";
		String declaringClass = EasyExecutionTest.class.getName() + "$3";
		String fileName = getClass().getSimpleName() + ".java";
		assertTrue(declaringClass.equals(array[0].getClassName()));
		assertTrue(declaringClass.equals(array[1].getClassName()));
		assertTrue(methodName.equals(array[0].getMethodName()));
		assertTrue(methodName.equals(array[1].getMethodName()));
		assertTrue(fileName.equals(array[0].getFileName()));
		assertTrue(fileName.equals(array[1].getFileName()));
		assertFalse(array[0] == array[1]);
	}
	
	@Test
	public void testStackStateDescription() throws Exception {
		final long[] id = { 0 };
		final String[] tallies = { null, null };
		Thread thread = new Thread() {
			@Override
			public void run() {
				id[0] = getId();
				tallies[0] = currentStackTally();
				Thread thread = new Thread() {
					@Override
					public void run() {
						tallies[1] = currentStackTally();
					}
				};
				thread.run();
			}
		};
		thread.start();
		thread.join();
		assertEquals(format("%d.%d", id[0], 1), tallies[0]);
		assertEquals(format("%d.%d", id[0], 2), tallies[1]);
	}
}
