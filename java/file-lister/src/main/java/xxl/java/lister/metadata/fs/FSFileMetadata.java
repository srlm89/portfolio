package xxl.java.lister.metadata.fs;

import java.io.File;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.FileMetadata;

public class FSFileMetadata extends FileMetadata {

	public FSFileMetadata(File file, DirectoryMetadata parentDirectory) {
		super(file.getName(), parentDirectory);
		this.file = file;
	}

	@Override
	public long sizeInBytes() {
		return file.length();
	}

	private File file;
}
