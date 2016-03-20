package xxl.java.lister.fs;

import java.io.File;

import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.SingleFileMetadata;

public class FileSystemSingleFileMetadata extends SingleFileMetadata {

	public FileSystemSingleFileMetadata(File file, DirectoryMetadata parentDirectory) {
		super(file.getName(), parentDirectory);
		this.file = file;
	}

	@Override
	public long sizeInBytes() {
		return file.length();
	}

	private File file;
}
