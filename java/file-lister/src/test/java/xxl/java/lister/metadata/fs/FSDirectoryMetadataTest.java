package xxl.java.lister.metadata.fs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.junit.Test;

import xxl.java.lister.metadata.DirectoryMetadata;

public class FSDirectoryMetadataTest {

	@Test
	public void getNameOfCurrentDirectory() throws IOException {
		String name = new File(".").getCanonicalFile().getName();
		FSDirectoryMetadata metadata = metadata(new File("."), null);
		assertEquals(name, metadata.name());
	}

	@Test
	@SuppressWarnings("serial")
	public void emptyArrayIfNull() {
		File directory = new File(".") {
			@Override
			public File[] listFiles(FileFilter filter) {
				return null;
			}
		};
		FSDirectoryMetadata metadata = metadata(directory, null);
		Iterable<? extends DirectoryMetadata> iterable = metadata.subDirectories();
		assertFalse(iterable.iterator().hasNext());
	}

	private FSDirectoryMetadata metadata(File directory, DirectoryMetadata parentDirectory) {
		return new FSDirectoryMetadata(directory, parentDirectory);
	}
}
