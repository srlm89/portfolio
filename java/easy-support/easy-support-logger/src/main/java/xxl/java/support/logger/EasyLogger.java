package xxl.java.support.logger;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EasyLogger {

	public static void logDebug(Object object, Iterable<String> lines) {
		logDebug(loggerFor(object), lines);
	}

	public static void logInfo(Object object, Iterable<String> lines) {
		logInfo(loggerFor(object), lines);
	}

	public static void logWarning(Object object, Iterable<String> lines) {
		logWarning(loggerFor(object), lines);
	}

	public static void logError(Object object, Iterable<String> lines) {
		logError(loggerFor(object), lines);
	}

	public static void logDebug(Object object, String... lines) {
		logDebug(loggerFor(object), lines);
	}

	public static void logInfo(Object object, String... lines) {
		logInfo(loggerFor(object), lines);
	}

	public static void logWarning(Object object, String... lines) {
		logWarning(loggerFor(object), lines);
	}

	public static void logError(Object object, String... lines) {
		logError(loggerFor(object), lines);
	}

	public static void logDebug(Object object, String message) {
		logDebug(loggerFor(object), message);
	}

	public static void logInfo(Object object, String message) {
		logInfo(loggerFor(object), message);
	}

	public static void logWarning(Object object, String message) {
		logWarning(loggerFor(object), message);
	}

	public static void logError(Object object, String message) {
		logError(loggerFor(object), message);
	}

	public static void logDebug(Logger logger, String... lines) {
		logDebug(logger, asList(lines));
	}

	public static void logInfo(Logger logger, String... lines) {
		logInfo(logger, asList(lines));
	}

	public static void logWarning(Logger logger, String... lines) {
		logWarning(logger, asList(lines));
	}

	public static void logError(Logger logger, String... lines) {
		logError(logger, asList(lines));
	}

	public static void logDebug(Logger logger, Iterable<String> lines) {
		logDebug(logger, joined(lines));
	}

	public static void logInfo(Logger logger, Iterable<String> lines) {
		logInfo(logger, joined(lines));
	}

	public static void logWarning(Logger logger, Iterable<String> lines) {
		logWarning(logger, joined(lines));
	}

	public static void logError(Logger logger, Iterable<String> lines) {
		logError(logger, joined(lines));
	}

	public static void logDebugLn(Logger logger, String message) {
		logDebug(logger, message + lineSeparator);
	}

	public static void logInfoLn(Logger logger, String message) {
		logInfo(logger, message + lineSeparator);
	}

	public static void logWarningLn(Logger logger, String message) {
		logWarning(logger, message + lineSeparator);
	}

	public static void logErrorLn(Logger logger, String message) {
		logError(logger, message + lineSeparator);
	}

	public static void logDebug(Logger logger, String message) {
		logger.debug(message);
	}

	public static void logInfo(Logger logger, String message) {
		logger.info(message);
	}

	public static void logWarning(Logger logger, String message) {
		logger.warn(message);
	}

	public static void logError(Logger logger, String message) {
		logger.error(message);
	}

	public static void logAsString(Object object, Iterable<? extends Object> elements) {
		logAsString(loggerFor(object), elements);
	}

	public static void logAsString(Logger logger, Iterable<? extends Object> elements) {
		logAsString(logger, "", elements);
	}

	public static void logAsString(Object object, String title, Iterable<? extends Object> elements) {
		logAsString(loggerFor(object), title, elements);
	}

	public static void logAsString(Logger logger, String title, Iterable<? extends Object> elements) {
		String string = joined(elements);
		if (title.length() > 0) {
			string = title + lineSeparator + string;
		}
		logDebug(logger, string);
	}

	private static Logger loggerFor(Object object) {
		Class<?> objectClass = object.getClass();
		if (Class.class.isInstance(object)) {
			objectClass = (Class<?>) object;
		}
		return loggerFor(objectClass);
	}

	private synchronized static Logger loggerFor(Class<?> aClass) {
		if (!loggers.containsKey(aClass)) {
			loggers.put(aClass, newLoggerFor(aClass));
		}
		return loggers.get(aClass);
	}

	private static Logger newLoggerFor(Class<?> aClass) {
		return LoggerFactory.getLogger(aClass);
	}

	private static String joined(Iterable<? extends Object> objects) {
		StringBuilder joined = new StringBuilder();
		Iterator<? extends Object> iterator = objects.iterator();
		if (iterator.hasNext()) {
			joined.append(iterator.next());
			while (iterator.hasNext()) {
				joined.append(lineSeparator).append(iterator.next().toString());
			}
		}
		return joined.toString();
	}

	private static String lineSeparator;
	private static Map<Class<?>, Logger> loggers;

	static {
		loggers = new HashMap<Class<?>, Logger>();
		lineSeparator = System.getProperty("line.separator");
	}
}
