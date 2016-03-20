package xxl.java.reflection;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

import java.lang.reflect.Method;

public class InvocationStopwatch {

	public static Object invoke(Method method, Object receiver, Object... arguments) {
		long start = currentTimeMillis();
		Object result = EasyClass.invoke(method, receiver, arguments);
		long finish = currentTimeMillis();
		log(format("[Invocation to '%s' on '%s': %d ms]", method.toString(), receiver.toString(), finish - start));
		return result;
	}

	private static void log(String message) {
		System.out.println(message);
	}
}
