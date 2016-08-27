package xxl.java.lister.metadata.fs;

import java.io.File;

import xxl.java.lister.metadata.PathMetadata;

public class FileSystemMetadata {

	public static PathMetadata withRoot(File file) {
		ensureExists(file);
		if (file.isDirectory()) {
			return new FSDirectoryMetadata(file, null);
		}
		else {
			return new FSFileMetadata(file, null);
		}
	}

	private static void ensureExists(File file) {
		if (!file.exists()) {
			throw new IllegalArgumentException("File does not exist: " + file);
		}
	}
}
