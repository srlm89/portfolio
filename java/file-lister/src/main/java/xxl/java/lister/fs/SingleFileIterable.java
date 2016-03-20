package xxl.java.lister.fs;

import java.io.File;

import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.SingleFileMetadata;

public class SingleFileIterable extends FileIterable<SingleFileMetadata> {

	protected SingleFileIterable(File[] files, DirectoryMetadata parentDirectory) {
		super(files, parentDirectory);
	}

	@Override
	protected SingleFileMetadata nextFrom(final File file, DirectoryMetadata directoryMetadata) {
		return new FileSystemSingleFileMetadata(file, directoryMetadata);
	}
}
