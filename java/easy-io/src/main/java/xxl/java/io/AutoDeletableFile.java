package xxl.java.io;

import static java.lang.String.format;
import static xxl.java.io.EasyFile.delete;

import java.io.File;
import java.io.IOException;

public class AutoDeletableFile implements AutoCloseable {

	public AutoDeletableFile(File parentDirectory, String fileName) {
		this(new File(parentDirectory, fileName));
	}

	public AutoDeletableFile(String absoluteFilePath) {
		this(new File(absoluteFilePath));
	}

	public AutoDeletableFile(File file) {
		this.file = file;
	}

	public File asExistent() {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(format("Unable to create file %s", file), e);
			} finally {
				if (!file.exists()) {
					throw new RuntimeException(format("Unable to create file %s", file));
				}
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
