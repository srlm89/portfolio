package xxl.java.lister.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.lister.test.util.TestUtil.toList;

import java.util.Collection;
import java.util.List;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.PathMetadata;
import xxl.java.lister.metadata.FileMetadata;

public abstract class PathMetadataTest {

	public static void checkBuilderTest1(PathMetadata builderTest1Metadata) {
		List<FileMetadata> files;
		List<DirectoryMetadata> subDirectories;
		Collection<String> subDirectoryNames;
		List<DirectoryMetadata> subDirectoriesForNames;
		DirectoryMetadata directoryMetadata;
		FileMetadata singleFileMetadata;

		assertTrue(builderTest1Metadata.isDirectory());
		assertFalse(builderTest1Metadata.isSingleFile());
		assertTrue(DirectoryMetadata.class.isInstance(builderTest1Metadata));

		directoryMetadata = (DirectoryMetadata) builderTest1Metadata;
		assertEquals("builder-test-1", directoryMetadata.name());
		assertEquals("/builder-test-1", directoryMetadata.filePath());
		assertEquals("/", directoryMetadata.parentPath());
		assertTrue(directoryMetadata.pathEquals("/builder-test-1"));
		files = toList(directoryMetadata.files());
		assertEquals(2, files.size());
		assertFalse(directoryMetadata.hasSingleFile("1"));
		assertTrue(directoryMetadata.hasSingleFile("1.txt"));
		assertFalse(directoryMetadata.hasSingleFile("2"));
		assertTrue(directoryMetadata.hasSingleFile("2.txt"));
		subDirectories = toList(directoryMetadata.subDirectories());
		assertEquals(1, subDirectories.size());
		assertTrue(directoryMetadata.hasSubDirectory("A"));
		assertFalse(directoryMetadata.hasSubDirectory("a"));
		subDirectoryNames = toList(directoryMetadata.subDirectoryNames());
		assertEquals(1, subDirectoryNames.size());
		assertTrue(subDirectoryNames.contains("A"));
		subDirectoriesForNames = toList(directoryMetadata.subDirectoriesFor(subDirectoryNames));
		assertEquals(1, subDirectoriesForNames.size());

		singleFileMetadata = files.get(0);
		assertEquals("1.txt", singleFileMetadata.name());
		assertEquals(2, singleFileMetadata.sizeInBytes());
		assertTrue(singleFileMetadata.pathEquals("/builder-test-1/1.txt"));
		assertEquals("/builder-test-1/1.txt", singleFileMetadata.filePath());
		assertEquals("/builder-test-1", singleFileMetadata.parentPath());
		assertEquals(directoryMetadata.singleFile("1.txt"), singleFileMetadata);
		assertEquals("1.txt (2)", singleFileMetadata.printableName());

		singleFileMetadata = files.get(1);
		assertEquals("2.txt", singleFileMetadata.name());
		assertEquals(3, singleFileMetadata.sizeInBytes());
		assertTrue(singleFileMetadata.pathEquals("/builder-test-1/2.txt"));
		assertEquals("/builder-test-1/2.txt", singleFileMetadata.filePath());
		assertEquals("/builder-test-1", singleFileMetadata.parentPath());
		assertEquals(directoryMetadata.singleFile("2.txt"), singleFileMetadata);
		assertEquals("2.txt (3)", singleFileMetadata.printableName());

		assertEquals(directoryMetadata.subDirectory("A"), subDirectories.get(0));
		directoryMetadata = subDirectories.get(0);
		assertEquals(subDirectoriesForNames.get(0), directoryMetadata);
		assertEquals("A", directoryMetadata.name());
		assertEquals("/builder-test-1/A", directoryMetadata.filePath());
		assertEquals("/builder-test-1", directoryMetadata.parentPath());
		assertTrue(directoryMetadata.pathEquals("/builder-test-1/A"));
		files = toList(directoryMetadata.files());
		assertEquals(1, files.size());
		assertFalse(directoryMetadata.hasSingleFile("3"));
		assertTrue(directoryMetadata.hasSingleFile("3.txt"));
		subDirectories = toList(directoryMetadata.subDirectories());
		assertEquals(2, subDirectories.size());
		assertTrue(directoryMetadata.hasSubDirectory("B"));
		assertFalse(directoryMetadata.hasSubDirectory("b"));
		assertTrue(directoryMetadata.hasSubDirectory("C"));
		assertFalse(directoryMetadata.hasSubDirectory("c"));
		subDirectoryNames = toList(directoryMetadata.subDirectoryNames());
		assertEquals(2, subDirectoryNames.size());
		assertTrue(subDirectoryNames.contains("B"));
		assertTrue(subDirectoryNames.contains("C"));
		subDirectoriesForNames = toList(directoryMetadata.subDirectoriesFor(subDirectoryNames));
		assertEquals(2, subDirectoriesForNames.size());

		singleFileMetadata = files.get(0);
		assertEquals("3.txt", singleFileMetadata.name());
		assertEquals(4, singleFileMetadata.sizeInBytes());
		assertTrue(singleFileMetadata.pathEquals("/builder-test-1/A/3.txt"));
		assertEquals("/builder-test-1/A/3.txt", singleFileMetadata.filePath());
		assertEquals("/builder-test-1/A", singleFileMetadata.parentPath());
		assertEquals(directoryMetadata.singleFile("3.txt"), singleFileMetadata);
		assertEquals("3.txt (4)", singleFileMetadata.printableName());

		assertEquals(directoryMetadata.subDirectory("C"), subDirectories.get(1));
		directoryMetadata = subDirectories.get(1);
		assertEquals(subDirectoriesForNames.get(1), directoryMetadata);
		assertEquals("C", directoryMetadata.name());
		assertEquals("/builder-test-1/A/C", directoryMetadata.filePath());
		assertEquals("/builder-test-1/A", directoryMetadata.parentPath());
		assertTrue(directoryMetadata.pathEquals("/builder-test-1/A/C"));
		files = toList(directoryMetadata.files());
		assertEquals(1, files.size());
		assertFalse(directoryMetadata.hasSingleFile("6"));
		assertTrue(directoryMetadata.hasSingleFile("6.txt"));
		assertTrue(toList(directoryMetadata.subDirectories()).isEmpty());
		assertTrue(toList(directoryMetadata.subDirectoryNames()).isEmpty());

		singleFileMetadata = files.get(0);
		assertEquals("6.txt", singleFileMetadata.name());
		assertEquals(7, singleFileMetadata.sizeInBytes());
		assertTrue(singleFileMetadata.pathEquals("/builder-test-1/A/C/6.txt"));
		assertEquals("/builder-test-1/A/C/6.txt", singleFileMetadata.filePath());
		assertEquals("/builder-test-1/A/C", singleFileMetadata.parentPath());
		assertEquals(directoryMetadata.singleFile("6.txt"), singleFileMetadata);
		assertEquals("6.txt (7)", singleFileMetadata.printableName());

		directoryMetadata = subDirectories.get(0);
		assertEquals(subDirectoriesForNames.get(0), directoryMetadata);
		assertEquals("B", directoryMetadata.name());
		assertEquals("/builder-test-1/A/B", directoryMetadata.filePath());
		assertEquals("/builder-test-1/A", directoryMetadata.parentPath());
		assertTrue(directoryMetadata.pathEquals("/builder-test-1/A/B"));
		files = toList(directoryMetadata.files());
		assertEquals(1, files.size());
		assertFalse(directoryMetadata.hasSingleFile("4"));
		assertTrue(directoryMetadata.hasSingleFile("4.txt"));
		subDirectories = toList(directoryMetadata.subDirectories());
		assertEquals(1, subDirectories.size());
		assertTrue(directoryMetadata.hasSubDirectory("D"));
		assertFalse(directoryMetadata.hasSubDirectory("d"));
		subDirectoryNames = toList(directoryMetadata.subDirectoryNames());
		assertEquals(1, subDirectoryNames.size());
		assertTrue(subDirectoryNames.contains("D"));
		subDirectoriesForNames = toList(directoryMetadata.subDirectoriesFor(subDirectoryNames));
		assertEquals(1, subDirectoriesForNames.size());

		singleFileMetadata = files.get(0);
		assertEquals("4.txt", singleFileMetadata.name());
		assertEquals(5, singleFileMetadata.sizeInBytes());
		assertTrue(singleFileMetadata.pathEquals("/builder-test-1/A/B/4.txt"));
		assertEquals("/builder-test-1/A/B/4.txt", singleFileMetadata.filePath());
		assertEquals("/builder-test-1/A/B", singleFileMetadata.parentPath());
		assertEquals(directoryMetadata.singleFile("4.txt"), singleFileMetadata);
		assertEquals("4.txt (5)", singleFileMetadata.printableName());

		assertEquals(directoryMetadata.subDirectory("D"), subDirectories.get(0));
		directoryMetadata = subDirectories.get(0);
		assertEquals(subDirectoriesForNames.get(0), directoryMetadata);
		assertEquals("D", directoryMetadata.name());
		assertEquals("/builder-test-1/A/B/D", directoryMetadata.filePath());
		assertEquals("/builder-test-1/A/B", directoryMetadata.parentPath());
		assertTrue(directoryMetadata.pathEquals("/builder-test-1/A/B/D"));
		files = toList(directoryMetadata.files());
		assertEquals(1, files.size());
		assertFalse(directoryMetadata.hasSingleFile("5"));
		assertTrue(directoryMetadata.hasSingleFile("5.txt"));
		assertTrue(toList(directoryMetadata.subDirectories()).isEmpty());
		assertTrue(toList(directoryMetadata.subDirectoryNames()).isEmpty());

		singleFileMetadata = files.get(0);
		assertEquals("5.txt", singleFileMetadata.name());
		assertEquals("/builder-test-1/A/B/D/5.txt", singleFileMetadata.filePath());
		assertEquals("/builder-test-1/A/B/D", singleFileMetadata.parentPath());
		assertTrue(singleFileMetadata.pathEquals("/builder-test-1/A/B/D/5.txt"));
		assertEquals(6, singleFileMetadata.sizeInBytes());
		assertEquals("5.txt (6)", singleFileMetadata.printableName());
	}

	protected FileMetadata newSingleFile(String name, final long size, DirectoryMetadata parent) {
		return new FileMetadata(name, parent) {
			@Override
			public long sizeInBytes() {
				return size;
			}
		};
	}

	protected DirectoryMetadata newDirectory(String name, final Iterable<? extends FileMetadata> files,
			final Iterable<? extends DirectoryMetadata> subDirectories, DirectoryMetadata parent) {
		return new DirectoryMetadata(name, parent) {

			@Override
			public Iterable<? extends FileMetadata> files() {
				return files;
			}

			@Override
			public Iterable<? extends DirectoryMetadata> subDirectories() {
				return subDirectories;
			}
		};
	}
}
