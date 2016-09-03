package xxl.java.io;

import static java.lang.String.format;
import static xxl.java.io.EasyFile.delete;

import java.io.File;

public class AutoDeletableDirectory implements AutoCloseable {

	public AutoDeletableDirectory(File parentDir, String dirName) {
		this(new File(parentDir, dirName));
	}

	public AutoDeletableDirectory(String absoluteDirPath) {
		this(new File(absoluteDirPath));
	}

	public AutoDeletableDirectory(File directory) {
		this.file = directory;
	}

	public File asExistent() {
		if (!file.exists()) {
			file.mkdir();
			if (!file.isDirectory()) {
				throw new RuntimeException(format("Unable to create directory %s", file));
			}
		}
		return file;
	}

	@Override
	public void close() {
		delete(file);
	}

	@Override
	public String toString() {
		return format("%s[%s]", getClass().getSimpleName(), file);
	}

	private File file;
}
