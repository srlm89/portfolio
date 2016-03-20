package xxl.java.lister.fs;

import java.io.File;

import xxl.java.lister.model.DirectoryMetadata;

public class DirectoryIterable extends FileIterable<DirectoryMetadata> {

	protected DirectoryIterable(File[] directories, DirectoryMetadata parentDirectory) {
		super(directories, parentDirectory);
	}

	@Override
	protected DirectoryMetadata nextFrom(final File subDirectory, DirectoryMetadata parentDirectory) {
		return new FileSystemDirectoryMetadata(subDirectory, parentDirectory);
	}
}
