package xxl.java.lister.zip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.lister.fs.FileSystemMetadataBuilder.fileMetadataFor;

import java.io.File;
import java.util.zip.ZipFile;

import org.junit.Test;

import xxl.java.lister.index.zip.FileMetadataSnapshot;
import xxl.java.lister.index.zip.ZipDirectoryMetadataIndex;
import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.FileMetadata;
import xxl.java.lister.model.FileMetadataTest;
import xxl.java.lister.test.util.TestUtil;

public class MetadataSnapshotTest extends TestUtil {

	@Test
	public void snapshotOfBuilderTest1() throws Exception {
		File file = builderTest1();
		String snapshotFile = testDir + "/snapshot-test.zip";

		FileMetadata metadata = fileMetadataFor(file);
		assertTrue(DirectoryMetadata.class.isInstance(metadata));

		FileMetadataSnapshot snapshot = newSnapshot(snapshotFile, (DirectoryMetadata) metadata);
		ZipFile snapshotZipFile = snapshot.snapshotFile();
		assertEquals(7, snapshotZipFile.size());
		assertFalse(null == snapshotZipFile.getEntry("index"));

		ZipDirectoryMetadataIndex index = new ZipDirectoryMetadataIndex(snapshotZipFile);
		DirectoryMetadata indexRoot = index.root();
		FileMetadataTest.checkBuilderTest1(indexRoot);

		assertTrue(new File(snapshotFile).delete());
	}

	private FileMetadataSnapshot newSnapshot(String snapshotFile, DirectoryMetadata metadata) throws Exception {
		return new FileMetadataSnapshot(snapshotFile, metadata);
	}
}
