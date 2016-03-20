package xxl.java.reflection;

public class EasyExecution {

	public static Thread currentThread() {
		return Thread.currentThread();
	}
	
	public static StackTraceElement[] currentStackTrace() {
		return currentThread().getStackTrace();
	}
	
	public static StackTraceElement currentStackFrame() {
		return currentStackTrace()[3];
	}
	
	public static StackTraceElement callingStackFrame() {
		return currentStackTrace()[4];
	}
	
	public static String currentStackTally() {
		Thread current = currentThread();
		StringBuilder description = new StringBuilder();
		description.append(current.getId());
		description.append('.');
		description.append(current.getStackTrace().length - 2);
		return description.toString();
	}
}
