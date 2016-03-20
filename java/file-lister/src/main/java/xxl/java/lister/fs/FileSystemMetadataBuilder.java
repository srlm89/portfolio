package xxl.java.lister.fs;

import java.io.File;

import xxl.java.lister.model.FileMetadata;

public class FileSystemMetadataBuilder {

	public static FileMetadata fileMetadataFor(File file) {
		ensureExists(file);
		if (file.isDirectory()) {
			return new FileSystemDirectoryMetadata(file, null);
		}
		else {
			return new FileSystemSingleFileMetadata(file, null);
		}
	}

	private static void ensureExists(File file) {
		if (!file.exists()) {
			throw new IllegalArgumentException("File does not exist: " + file);
		}
	}
}
