package xxl.java.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static xxl.java.io.EasyFile.filesIn;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZipExtractorTest {

	private File testDir;
	
	@Before
	public void setUp() throws Exception {
		this.testDir = Files.createTempDirectory("_easy-file-test_").toFile();
	}
	
	@After
	public void tearDown() {
		EasyFile.delete(testDir);
	}
	
	@Test
	public void extractingZipFile() throws IOException {
		String zipFilePath = resource("files.zip").getPath();
		ZipExtractor extractor = new ZipExtractor(zipFilePath);
		File extractedDirectory = extractor.extractTo(testDir + "/zip-test-1");
		extractor.close();
		
		File rtfFile = resource("files/file.rtf");
		File folder = resource("files/folder");
		File picture1 = resource("files/folder/picture1.jpg");
		File picture2  = resource("files/folder/picture2.jpg");
		File picture3  = resource("files/folder/picture3.jpg");
		
		Map<String, File> files = filesIn(extractedDirectory);
		assertTrue(files.containsKey(rtfFile.getName()));
		assertTrue(rtfFile.length() == files.get(rtfFile.getName()).length());
		assertTrue(files.containsKey(folder.getName()));
		assertTrue(files.get(folder.getName()).isDirectory());
		
		files = filesIn(files.get(folder.getName()));
		assertTrue(files.containsKey(picture1.getName()));
		assertTrue(picture1.length() == files.get(picture1.getName()).length());
		assertTrue(files.containsKey(picture2.getName()));
		assertTrue(picture2.length() == files.get(picture2.getName()).length());
		assertTrue(files.containsKey(picture3.getName()));
		assertTrue(picture3.length() == files.get(picture3.getName()).length());
	}
	
	@Test
	public void canExtractUntilIsClosed() throws IOException {
		String zipFilePath = resource("files.zip").getPath();
		ZipExtractor extractor = new ZipExtractor(zipFilePath);
		File extractedA = extractor.extractTo(testDir + "/zip-test-2");
		File extractedB = extractor.extractTo(testDir + "/zip-test-3");
		
		assertTrue(extractedA.exists());
		assertTrue(extractedB.exists());
		assertTrue(extractedA.getName().equals("zip-test-2"));
		assertTrue(extractedB.getName().equals("zip-test-3"));
		
		extractor.close();
		try {
			extractor.extractTo(testDir + "/zip-test-4");
			fail();
		} catch (IOException e) {
			return;
		}
	}
	
	private File resource(String resourceName) {
		URL resource = getClass().getResource(resourceName);
		return new File(resource.getFile());
	}
}
