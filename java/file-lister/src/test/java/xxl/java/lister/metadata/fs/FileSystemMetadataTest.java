package xxl.java.lister.metadata.fs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static xxl.java.lister.metadata.fs.FileSystemMetadata.withRoot;

import java.io.File;

import org.junit.Test;

import xxl.java.lister.metadata.PathMetadataTest;
import xxl.java.lister.metadata.PathMetadata;
import xxl.java.lister.test.util.TestUtil;

public class FileSystemMetadataTest extends TestUtil {

	@Test
	public void exceptionIfInvalidFile() {
		File folder = resource("DirA");
		File file = new File(folder, "non-existent-file.txt");
		assertFalse(file.exists());
		try {
			withRoot(file);
			fail();
		}
		catch (IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void checkBuilderTest1() {
		File file = builderTest1();
		PathMetadata metadata = withRoot(file);
		PathMetadataTest.checkBuilderTest1(metadata);
	}
}
