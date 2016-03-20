package xxl.java.reflection;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class EasyJava {

	public static String asClasspath(URL[] urls) {
		StringBuilder builder = new StringBuilder();
		int size = urls.length;
		if (size > 0) {
			builder.append(urls[0].getPath());
			for (int i = 1; i < size; i += 1) {
				builder.append(classpathSeparator()).append(urls[i].getPath());
			}
		}
		return builder.toString();
	}

	public static String[] asFilePath(URL[] urls) {
		int length = urls.length;
		String[] filePath = new String[length];
		for (int i = 0; i < length; i += 1) {
			filePath[i] = urls[i].getFile();
		}
		return filePath;
	}

	public static String simpleClassName(String qualifiedClassName) {
		return qualifiedClassName.replaceFirst("^(.*[.$])*([^.$]+)$", "$2");
	}

	public static String packageName(String qualifiedClassName) {
		return qualifiedClassName.replaceFirst("^(?:(.*)[.][^.]+|[^.]+)$", "$1");
	}

	public static String systemClasspath() {
		return property("java.class.path");
	}

	public static URL[] systemClasspathURLs() throws IOException {
		return classpathFrom(systemClasspath());
	}

	public static void extendSystemClasspathWith(URL[] classpaths) {
		StringBuilder newClasspath = new StringBuilder(systemClasspath());
		for (URL classpath : classpaths) {
			newClasspath.append(classpathSeparator() + classpath.getPath());
		}
		setClasspath(newClasspath.toString());
	}

	public static URL[] extendClasspathWith(String classpath, URL[] destination) throws IOException {
		List<URL> extended = new LinkedList<URL>(asList(destination));
		extended.addAll(asList(classpathFrom(classpath)));
		return extended.toArray(new URL[extended.size()]);
	}

	public static URL[] classpathFrom(String classpath) throws IOException {
		List<String> names = asList(classpath.split(classpathSeparator()));
		URL[] folders = new URL[names.size()];
		int index = 0;
		for (String name : names) {
			folders[index] = new URL(name);
			index += 1;
		}
		return folders;
	}

	public static void setClasspath(String newClasspath) {
		setProperty("java.class.path", newClasspath);
	}

	public static Class<?> classFromClasspath(URL classpath, String qualifiedName) {
		Collection<Class<?>> classes = classesFromClasspath(new URL[] { classpath }, asList(qualifiedName));
		if (classes.isEmpty()) {
			return null;
		}
		return classes.iterator().next();
	}

	public static Collection<Class<?>> classesFromClasspath(URL[] classpath, Collection<String> qualifiedNames) {
		URLClassLoader loader = new URLClassLoader(classpath);
		Collection<Class<?>> classes = loadedClassesFrom(loader, qualifiedNames);
		close(loader);
		return classes;
	}

	public static Collection<Class<?>> loadedClassesFrom(ClassLoader classLoader, Collection<String> qualifiedNames) {
		Collection<Class<?>> classes = new ArrayList<Class<?>>(qualifiedNames.size());
		try {
			for (String qualifiedName : qualifiedNames) {
				classes.add(classLoader.loadClass(qualifiedName));
			}
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(format("Could not load classes %s", qualifiedNames.toString()), e);
		}
		return classes;
	}

	public static void close(URLClassLoader loader) {
		try {
			loader.close();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Could not close URLClassLoader properly");
		}
	}

	public static String lineSeparator() {
		return lineSeparator;
	}

	public static String classpathSeparator() {
		return classpathSeparator;
	}

	public static String folderPathSeparator() {
		return folderPathSeparator;
	}

	private static String property(String key) {
		return System.getProperty(key);
	}

	private static void setProperty(String key, String value) {
		System.setProperty(key, value);
	}

	private static final String lineSeparator;
	private static final String classpathSeparator;
	private static final String folderPathSeparator;

	static {
		lineSeparator = property("line.separator");
		folderPathSeparator = String.valueOf(File.separatorChar);
		classpathSeparator = String.valueOf(File.pathSeparatorChar);
	}
}
