package xxl.java.lister.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import xxl.java.lister.index.DirectoryMetadataIndex;
import xxl.java.lister.index.IndexedDirectoryMetadata;
import xxl.java.lister.index.IndexedSingleFileMetadata;
import xxl.java.lister.index.local.HashDirectoryMetadataIndex;
import xxl.java.lister.model.FileMetadataTest;

public class HashDirectoryMetadataIndexTest {

	@Test
	public void gettingNameFromPath() {
		DirectoryMetadataIndex index = newIndex();
		assertEquals("", index.nameFrom("/"));
		assertEquals("file", index.nameFrom("/file"));
		assertEquals("file", index.nameFrom("file"));
		assertEquals("file", index.nameFrom("/dir/file"));
		assertEquals("file", index.nameFrom("dir/file"));
		assertEquals("file", index.nameFrom("/dirA/dirB/file"));
		assertEquals("file", index.nameFrom("dirA/dirB/file"));
	}

	@Test
	public void gettingParentPathFromPath() {
		DirectoryMetadataIndex index = newIndex();
		assertEquals("/", index.parentPath("/"));
		assertEquals("/", index.parentPath("/file"));
		assertEquals("/", index.parentPath("file"));
		assertEquals("/dir", index.parentPath("/dir/file"));
		assertEquals("/dir", index.parentPath("dir/file"));
		assertEquals("/dirA/dirB", index.parentPath("/dirA/dirB/file"));
		assertEquals("/dirA/dirB", index.parentPath("dirA/dirB/file"));
	}

	@Test
	public void addingDirectories() {
		HashDirectoryMetadataIndex index = newIndex();
		IndexedDirectoryMetadata root = index.root();

		assertFalse(null == root);
		assertTrue(index.hasDirectory("/"));
		assertTrue(null == index.parentDirectoryOf("/"));
		assertEquals("/", root.filePath());
		assertEquals(root, index.parentDirectoryOf("/anyDirectory"));
		assertEquals(root, index.parentDirectoryOf("anyDirectory"));
		assertFalse(index.hasDirectory("anyDirectory"));
		assertFalse(index.hasDirectory("/anyDirectory"));
		assertFalse(root.hasSubDirectory("anyDirectory"));
		assertFalse(root.hasSubDirectory("/anyDirectory"));

		IndexedDirectoryMetadata newDirectory = index.directoryAddIfAbsent("anyDirectory");
		assertTrue(index.hasDirectory("anyDirectory"));
		assertTrue(index.hasDirectory("/anyDirectory"));
		assertTrue(root.hasSubDirectory("anyDirectory"));
		assertFalse(root.hasSubDirectory("/anyDirectory"));
		assertEquals(newDirectory, root.subDirectory("anyDirectory"));
		assertEquals(newDirectory, index.parentDirectoryOf("/anyDirectory/subDirectory"));
		assertEquals(newDirectory, index.parentDirectoryOf("anyDirectory/subDirectory"));
	}

	@Test
	public void addingFiles() {
		HashDirectoryMetadataIndex index = newIndex();
		IndexedDirectoryMetadata root = index.root();

		assertFalse(null == root);
		assertFalse(index.hasSingleFile("/"));
		assertFalse(index.hasSingleFile("/anyFile"));
		assertFalse(index.hasSingleFile("anyFile"));
		assertEquals(root, index.parentDirectoryOf("/anyFile"));
		assertEquals(root, index.parentDirectoryOf("anyFile"));
		assertFalse(root.hasSingleFile("/anyDirectory"));
		assertFalse(root.hasSingleFile("anyDirectory"));

		IndexedSingleFileMetadata newFile = index.singleFileAddIfAbsent("anyFile");
		assertTrue(index.hasSingleFile("anyFile"));
		assertTrue(index.hasSingleFile("/anyFile"));
		assertTrue(root.hasSingleFile("anyFile"));
		assertFalse(root.hasSingleFile("/anyFile"));
		assertEquals(newFile, root.singleFile("anyFile"));
		assertTrue(null == index.parentDirectoryOf("/anyFile/subDirectory"));
		assertTrue(null == index.parentDirectoryOf("anyFile/subDirectory"));
	}

	@Test
	public void builderTest1() {
		HashDirectoryMetadataIndex index = newIndex();

		index.singleFileAddIfAbsent("/builder-test-1/1.txt").withSize(2);
		assertTrue(index.hasDirectory("/builder-test-1"));
		assertTrue(index.directoryAddIfAbsent("/builder-test-1").hasSingleFile("1.txt"));
		assertTrue(2 == index.directoryAddIfAbsent("/builder-test-1").singleFile("1.txt").sizeInBytes());

		index.singleFileAddIfAbsent("/builder-test-1/2.txt").withSize(3);
		assertTrue(index.directoryAddIfAbsent("/builder-test-1").hasSingleFile("2.txt"));
		assertTrue(3 == index.directoryAddIfAbsent("/builder-test-1").singleFile("2.txt").sizeInBytes());

		index.singleFileAddIfAbsent("/builder-test-1/A/3.txt").withSize(4);
		assertTrue(index.hasDirectory("/builder-test-1/A"));
		assertTrue(index.directoryAddIfAbsent("/builder-test-1/A").hasSingleFile("3.txt"));
		assertTrue(4 == index.directoryAddIfAbsent("/builder-test-1/A").singleFile("3.txt").sizeInBytes());

		index.singleFileAddIfAbsent("/builder-test-1/A/B/4.txt").withSize(5);
		assertTrue(index.hasDirectory("/builder-test-1/A/B"));
		assertTrue(index.directoryAddIfAbsent("/builder-test-1/A/B").hasSingleFile("4.txt"));
		assertTrue(5 == index.directoryAddIfAbsent("/builder-test-1/A/B").singleFile("4.txt").sizeInBytes());

		index.singleFileAddIfAbsent("/builder-test-1/A/B/D/5.txt").withSize(6);
		assertTrue(index.hasDirectory("/builder-test-1/A/B/D"));
		assertTrue(index.directoryAddIfAbsent("/builder-test-1/A/B/D").hasSingleFile("5.txt"));
		assertTrue(6 == index.directoryAddIfAbsent("/builder-test-1/A/B/D").singleFile("5.txt").sizeInBytes());

		index.singleFileAddIfAbsent("/builder-test-1/A/C/6.txt").withSize(7);
		assertTrue(index.hasDirectory("/builder-test-1/A/C"));
		assertTrue(index.directoryAddIfAbsent("/builder-test-1/A/C").hasSingleFile("6.txt"));
		assertTrue(7 == index.directoryAddIfAbsent("/builder-test-1/A/C").singleFile("6.txt").sizeInBytes());

		assertTrue(index.root().hasSubDirectory("builder-test-1"));
		IndexedDirectoryMetadata builderTest1 = index.root().subDirectory("builder-test-1");
		FileMetadataTest.checkBuilderTest1(builderTest1);
	}

	private HashDirectoryMetadataIndex newIndex() {
		return new HashDirectoryMetadataIndex();
	}
}
