package xxl.java.lister.index.zip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static xxl.java.lister.metadata.fs.FileSystemMetadata.withRoot;

import java.io.File;
import java.util.zip.ZipFile;

import org.junit.Test;

import xxl.java.lister.index.zip.MetadataZipSnapshot;
import xxl.java.lister.index.zip.ZipMetadataIndex;
import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.PathMetadataTest;
import xxl.java.lister.metadata.PathMetadata;
import xxl.java.lister.test.util.TestUtil;

public class MetadataZipSnapshotTest extends TestUtil {

	@Test
	public void snapshotOfBuilderTest1() throws Exception {
		File file = builderTest1();
		String snapshotFile = testDir + "/snapshot-test.zip";

		PathMetadata metadata = withRoot(file);
		assertTrue(DirectoryMetadata.class.isInstance(metadata));

		MetadataZipSnapshot snapshot = newSnapshot(snapshotFile, (DirectoryMetadata) metadata);
		ZipFile snapshotZipFile = snapshot.snapshotFile();
		assertEquals(7, snapshotZipFile.size());
		assertFalse(null == snapshotZipFile.getEntry("index"));

		ZipMetadataIndex index = new ZipMetadataIndex(snapshotZipFile);
		DirectoryMetadata indexRoot = index.root();
		PathMetadataTest.checkBuilderTest1(indexRoot);

		assertTrue(new File(snapshotFile).delete());
	}

	private MetadataZipSnapshot newSnapshot(String snapshotFile, DirectoryMetadata metadata) throws Exception {
		return new MetadataZipSnapshot(snapshotFile, metadata);
	}
}
