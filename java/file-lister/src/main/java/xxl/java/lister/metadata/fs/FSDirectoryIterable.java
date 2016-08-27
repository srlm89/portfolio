package xxl.java.lister.metadata.fs;

import java.io.File;

public class FSDirectoryIterable extends FSPathIterable<FSDirectoryMetadata> {

	protected FSDirectoryIterable(File[] directories, FSDirectoryMetadata parentDirectory) {
		super(directories, parentDirectory);
	}

	@Override
	protected FSDirectoryMetadata nextFrom(File subDirectory, FSDirectoryMetadata parentDirectory) {
		return new FSDirectoryMetadata(subDirectory, parentDirectory);
	}
}
