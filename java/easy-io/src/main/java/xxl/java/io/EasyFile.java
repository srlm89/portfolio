package xxl.java.io;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EasyFile {

	public static String currentAbsolutePath() throws IOException {
		return openDirectoryFrom(".").getAbsolutePath();
	}

	public static boolean isValidFilePath(String path) {
		File file = new File(path);
		return file.exists() && file.isFile();
	}

	public static boolean isValidDirectoryPath(String path) {
		File file = new File(path);
		return file.exists() && file.isDirectory();
	}

	public static void ensureIsValidFilePath(String path) throws IOException {
		if (!isValidFilePath(path)) {
			throw new IOException(format("Path '%s' does not refer to a file", path));
		}
	}

	public static void ensureIsValidDirectoryPath(String path) throws IOException {
		if (!isValidDirectoryPath(path)) {
			throw new IOException(format("Path '%s' does not refer to a directory", path));
		}
	}

	public static File directoryCreateIfAbsent(String parent, String name) throws IOException {
		return directoryCreateIfAbsent(new File(parent, name));
	}

	public static File directoryCreateIfAbsent(File parent, String name) throws IOException {
		return directoryCreateIfAbsent(new File(parent, name));
	}

	public static File directoryCreateIfAbsent(String name) throws IOException {
		return directoryCreateIfAbsent(new File(name));
	}

	public static File directoryCreateIfAbsent(File directory) throws IOException {
		if (directory.exists() && !directory.isDirectory()) {
			throw new IOException("Invalid directory name: " + directory);
		}
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory;
	}

	public static File fileCreateIfAbsent(String name) throws IOException {
		return fileCreateIfAbsent(new File(name));
	}

	public static File fileCreateIfAbsent(String directory, String name) throws IOException {
		return fileCreateIfAbsent(new File(directory, name));
	}

	public static File fileCreateIfAbsent(File directory, String name) throws IOException {
		return fileCreateIfAbsent(new File(directory, name));
	}

	public static File fileCreateIfAbsent(File file) throws IOException {
		if (file.exists() && !file.isFile()) {
			throw new IOException("Invalid file name: " + file);
		}
		if (!file.exists()) {
			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}
			file.createNewFile();
		}
		return file;
	}

	public static File openFileFrom(String path) throws IOException {
		ensureIsValidFilePath(path);
		return new File(path);
	}

	public static File openDirectoryFrom(String path) throws IOException {
		ensureIsValidDirectoryPath(path);
		return new File(path);
	}

	public static boolean delete(File fileToDelete) {
		if (!fileToDelete.exists()) {
			return false;
		}
		LinkedList<File> files = new LinkedList<File>();
		files.add(fileToDelete);
		while (! files.isEmpty()) {
			File file = files.peek();
			File[] subFiles = file.listFiles();
			if (subFiles != null && subFiles.length > 0) {
				for (File subFile : file.listFiles()) {
					files.push(subFile);
				}
			}
			else {
				file.delete();
				files.pop();
			}
		}
		return !fileToDelete.exists();
	}

	public static void delete(Collection<File> files) {
		for (File file : files) {
			delete(file);
		}
	}

	public static String generatedDirectoryName(String name) {
		long time = currentTimeMillis();
		return format("%s-%d", name, time);
	}

	public static String generatedFileName(String prefix, String suffix) {
		long time = currentTimeMillis();
		if (!suffix.startsWith(".")) {
			suffix = "." + suffix;
		}
		return format("%s-%d%s", prefix, time, suffix);
	}

	public static boolean isSameFile(File aFile, File otherFile) {
		try {
			return aFile.getCanonicalPath().equals(otherFile.getCanonicalPath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Collection<File> filesMatchingNameIn(String directoryPath, String regexToMatch) throws IOException {
		return filesMatchingNameIn(new File(directoryPath), regexToMatch);
	}

	public static Collection<File> filesMatchingNameIn(File directory, String regexToMatch) throws IOException {
		File openDirectory = openDirectoryFrom(directory.getAbsolutePath());
		Collection<File> matchingFiles = new LinkedList<File>();
		for (File file : openDirectory.listFiles()) {
			if (file.getName().matches(regexToMatch)) {
				matchingFiles.add(file);
			}
		}
		return matchingFiles;
	}

	public static Map<String, File> filesIn(File directory) throws IOException {
		return filesIn(directory.getAbsolutePath());
	}

	public static Map<String, File> filesIn(String directoryPath) throws IOException {
		Map<String, File> files = new HashMap<String, File>();
		File openDirectory = openDirectoryFrom(directoryPath);
		File[] fileList = openDirectory.listFiles();
		for (File file : fileList) {
			files.put(file.getName(), file);
		}
		return files;
	}

	public static URL urlFrom(String path) throws IOException {
		URL url = null;
		try {
			url = openFileFrom(path).toURI().toURL();
		}
		catch (MalformedURLException e) {
			throw new IllegalArgumentException(format("Illegal name for '%s' while converting to URL", path));
		}
		return url;
	}
}
