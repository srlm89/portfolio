package xxl.java.lister.metadata.fs;

import java.io.File;

import xxl.java.lister.metadata.FileMetadata;

public class FSFileIterable extends FSPathIterable<FileMetadata> {

	protected FSFileIterable(File[] files, FSDirectoryMetadata parentDirectory) {
		super(files, parentDirectory);
	}

	@Override
	protected FileMetadata nextFrom(File file, FSDirectoryMetadata directoryMetadata) {
		return new FSFileMetadata(file, directoryMetadata);
	}
}
