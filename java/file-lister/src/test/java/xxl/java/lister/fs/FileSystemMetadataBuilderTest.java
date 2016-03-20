package xxl.java.lister.fs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static xxl.java.lister.fs.FileSystemMetadataBuilder.fileMetadataFor;

import java.io.File;

import org.junit.Test;

import xxl.java.lister.model.FileMetadata;
import xxl.java.lister.model.FileMetadataTest;
import xxl.java.lister.test.util.TestUtil;

public class FileSystemMetadataBuilderTest extends TestUtil {

	@Test
	public void exceptionIfInvalidFile() {
		File folder = resource("DirA");
		File file = new File(folder, "non-existent-file.txt");
		assertFalse(file.exists());
		try {
			fileMetadataFor(file);
			fail();
		}
		catch (IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void checkBuilderTest1() {
		File file = builderTest1();
		FileMetadata metadata = fileMetadataFor(file);
		FileMetadataTest.checkBuilderTest1(metadata);
	}
}
