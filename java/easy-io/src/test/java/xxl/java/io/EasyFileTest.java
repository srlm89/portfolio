package xxl.java.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static xxl.java.io.EasyFile.delete;
import static xxl.java.io.EasyFile.directoryCreateIfAbsent;
import static xxl.java.io.EasyFile.ensureIsValidDirectoryPath;
import static xxl.java.io.EasyFile.ensureIsValidFilePath;
import static xxl.java.io.EasyFile.fileCreateIfAbsent;
import static xxl.java.io.EasyFile.filesIn;
import static xxl.java.io.EasyFile.filesMatchingNameIn;
import static xxl.java.io.EasyFile.generatedDirectoryName;
import static xxl.java.io.EasyFile.generatedFileName;
import static xxl.java.io.EasyFile.isValidDirectoryPath;
import static xxl.java.io.EasyFile.isValidFilePath;
import static xxl.java.io.EasyFile.openDirectoryFrom;
import static xxl.java.io.EasyFile.openFileFrom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EasyFileTest {

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
	public void fileCreation() throws IOException {
		String dir = testDir.getAbsolutePath();
		String baseName1 = dir + "/test-file-1";
		String baseName2 = dir + "/test-file-2";
		String baseName3 = "/test-file-3";
		String baseName4 = "/test-file-4";

		String name1 = fileNameFor(baseName1);
		String name2 = fileNameFor(baseName2);
		String name3 = fileNameFor(baseName3);
		String name4 = fileNameFor(baseName4);
		
		assertFalse(isValidFilePath(name1));
		assertFalse(isValidFilePath(name2));
		assertFalse(isValidFilePath(dir + name3));
		assertFalse(isValidFilePath(dir + name4));
		
		checkAndDelete(fileCreateIfAbsent(name1), name1, false);
		checkAndDelete(fileCreateIfAbsent(new File(name2)), name2, false);
		checkAndDelete(fileCreateIfAbsent(dir, name3), dir + name3, false);
		checkAndDelete(fileCreateIfAbsent(new File(dir), name4), dir + name4, false);
		assertTrue(testDir.delete());
	}
	
	@Test
	public void directoryCreation() throws IOException {
		String dir = testDir.getAbsolutePath();
		String baseName1 = dir + "/test-dir-1";
		String baseName2 = dir + "/test-dir-2";
		String baseName3 = "/test-dir-3";
		String baseName4 = "/test-dir-4";

		String name1 = dirNameFor(baseName1);
		String name2 = dirNameFor(baseName2);
		String name3 = dirNameFor(baseName3);
		String name4 = dirNameFor(baseName4);
		
		assertFalse(isValidDirectoryPath(name1));
		assertFalse(isValidDirectoryPath(name2));
		assertFalse(isValidDirectoryPath(dir + name3));
		assertFalse(isValidDirectoryPath(dir + name4));
		
		checkAndDelete(directoryCreateIfAbsent(name1), name1, true);
		checkAndDelete(directoryCreateIfAbsent(new File(name2)), name2, true);
		checkAndDelete(directoryCreateIfAbsent(dir, name3), dir + name3, true);
		checkAndDelete(directoryCreateIfAbsent(new File(dir), name4), dir + name4, true);
		assertTrue(testDir.delete());
	}
	
	@Test
	@SuppressWarnings("unused")
	public void matchingFilesFromDirectory() throws IOException {
		Collection<File> matching;
		File directory = directoryCreateIfAbsent(testDir.getAbsolutePath() + "/matching-directory");
		File subFileA = fileCreateIfAbsent(directory, "file-A-waz");
		File subFileB = fileCreateIfAbsent(directory, "file-B-zxw");
		File subFolder = directoryCreateIfAbsent(directory, "subdir-A-asz");
		try {
			matching = filesMatchingNameIn(directory.getAbsolutePath(), "file-A.*");
			assertEquals(1, matching.size());
			assertEquals(subFileA.getAbsolutePath(), matching.iterator().next().getAbsolutePath());
			
			matching = filesMatchingNameIn(directory, ".*-asz");
			assertEquals(1, matching.size());
			assertEquals(subFolder.getAbsolutePath(), matching.iterator().next().getAbsolutePath());
			
			matching = filesMatchingNameIn(directory, "file.*");
			assertEquals(2, matching.size());
			
			matching = filesMatchingNameIn(directory, ".*z(|..)");
			assertEquals(3, matching.size());
		}
		finally {
			assertTrue(delete(directory));
		}
	}
	
	@Test
	public void mappedFiles() throws IOException {
		File directory = directoryCreateIfAbsent(testDir.getAbsolutePath() + "/mapped");
		File subFileA = fileCreateIfAbsent(directory, "file-A-waz");
		File subFileB = fileCreateIfAbsent(directory, "file-B-zxw");
		File subFolder = directoryCreateIfAbsent(directory, "subdir-A-asz");
		Map<String, File> files = filesIn(directory.getAbsolutePath());
		assertEquals(3, files.size());
		assertTrue(files.containsKey(subFileA.getName()));
		assertEquals(subFileA.getAbsolutePath(), files.get(subFileA.getName()).getAbsolutePath());
		assertTrue(files.containsKey(subFileB.getName()));
		assertEquals(subFileB.getAbsolutePath(), files.get(subFileB.getName()).getAbsolutePath());
		assertTrue(files.containsKey(subFileA.getName()));
		assertEquals(subFolder.getAbsolutePath(), files.get(subFolder.getName()).getAbsolutePath());
		assertTrue(delete(directory));
	}
	
	@Test
	public void ensurePaths() throws IOException {
		boolean thrown;
		File directory = directoryCreateIfAbsent(testDir.getAbsolutePath() + "/ensure-paths");
		assertEquals(0, directory.listFiles().length);
		File subDirectory = directoryCreateIfAbsent(directory, "subdir");
		File file = fileCreateIfAbsent(directory, "file");
		try {
			ensureIsValidFilePath(file.getAbsolutePath());
			ensureIsValidDirectoryPath(subDirectory.getAbsolutePath());
		} catch (IOException e) {
			fail();
		}
		
		thrown = false;
		try {
			ensureIsValidDirectoryPath(directory.getAbsolutePath() + "/subdir2");
		} catch (IOException e) {
			thrown = true;
		}
		assertTrue(thrown);
		
		thrown = false;
		try {
			ensureIsValidFilePath(directory.getAbsolutePath() + "/file2");
		} catch (IOException e) {
			thrown = true;
		}
		assertTrue(thrown);
		assertTrue(delete(directory));
	}
	
	private void checkAndDelete(File createdFile, String absolutePath, boolean isDirectory) throws IOException {
		String actualAbsolutePath = createdFile.getAbsolutePath();
		assertTrue(createdFile.exists());
		assertEquals(absolutePath, actualAbsolutePath);
		assertEquals(isDirectory, createdFile.isDirectory());
		checkOpen(absolutePath, isDirectory);
		checkCreationFails(absolutePath, isDirectory);
		assertTrue(delete(createdFile));
	}
	
	private void checkCreationFails(String path, boolean isDirectory) throws IOException {
		if (isDirectory) {
			try {
				assertTrue(isValidDirectoryPath(path));
				fileCreateIfAbsent(path);
			} catch (IOException e) {
				return;
			}
			fail();
		} else {
			try {
				assertTrue(isValidFilePath(path));
				directoryCreateIfAbsent(path);
			} catch (IOException e) {
				return;
			}
			fail();
		}
	}

	private void checkOpen(String path, boolean isDirectory) {
		boolean failedFile = false;
		boolean failedDirectory = false;
		try {
			openFileFrom(path);
		} catch (IOException e) {
			failedFile = true;
		}
		try {
			openDirectoryFrom(path);
		}  catch (IOException e) {
			failedDirectory = true;
		}
		assertEquals(isDirectory, failedFile);
		assertEquals(isDirectory, isValidDirectoryPath(path));
		assertEquals(! isDirectory, failedDirectory);
		assertEquals(! isDirectory, isValidFilePath(path));
	}
	
	private String dirNameFor(String baseName) {
		String name = generatedDirectoryName(baseName);
		assertTrue(name.matches(baseName + "-[0-9]+"));
		return name;
	}
	
	private String fileNameFor(String baseName) {
		String name = generatedFileName(baseName, ".txt");
		assertTrue(name.matches(baseName + "-[0-9]+.txt"));
		return name;
	}
}
