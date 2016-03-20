package xxl.java.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZipCompressorTest {

	private File testDir;
	
	@Before
	public void setUp() throws Exception {
		this.testDir = Files.createTempDirectory("_zip-compressor-test_").toFile();
	}
	
	@After
	public void tearDown() {
		EasyFile.delete(testDir);
	}
	
	@Test
	public void zipSingleFile() throws IOException {
		File singleFile = resource("Singlefile-1.txt");
		String zipFileName = testDir + "/singleFile-1.zip";

		ZipCompressor compressor = newCompressor(zipFileName);
		compressor.add(singleFile);
		compressor.close();
		
		assertFalse(1 == compressor.checksum());
		assertEquals(1, compressor.files());
		assertEquals(0, compressor.folders());

		ZipFile zipFile = compressor.zipFile();
		assertEquals(1, zipFile.size());
		assertEquals(1, compressor.entries());
		
		ZipEntry entry = zipFile.getEntry("Singlefile-1.txt");
		assertFalse(entry == null);
		assertEquals(41, entry.getSize());
		assertTrue(new File(zipFileName).delete());
	}

	@Test
	public void zipFolderWithFile() throws IOException {
		File dir = resource("Directory-1");
		String zipFileName = testDir + "/singleFile-2.zip";
		
		ZipCompressor compressor = newCompressor(zipFileName);
		compressor.add(dir);
		compressor.close();
		
		assertFalse(1 == compressor.checksum());
		assertEquals(1, compressor.files());
		assertEquals(1, compressor.folders());

		ZipFile zipFile = compressor.zipFile();
		assertEquals(1, zipFile.size());
		assertEquals(1, compressor.entries());
		
		ZipEntry entry = zipFile.getEntry("Directory-1/Singlefile-1.txt");
		assertFalse(entry == null);
		assertEquals(41, entry.getSize());
		assertTrue(new File(zipFileName).delete());
	}

	@Test
	public void nestedFolders() throws IOException {
		File dir = resource("Directory-2");
		String zipFileName = testDir + "/singleFile-3.zip";
		
		ZipCompressor compressor = newCompressor(zipFileName);
		compressor.add(dir);
		compressor.close();
		
		assertFalse(1 == compressor.checksum());
		assertEquals(1, compressor.files());
		assertEquals(2, compressor.folders());

		ZipFile zipFile = compressor.zipFile();
		assertEquals(1, zipFile.size());
		assertEquals(1, compressor.entries());

		ZipEntry entry = zipFile.getEntry("Directory-2/test-semcc/Singlefile-1.txt");
		assertFalse(entry == null);
		assertEquals(41, entry.getSize());
		assertTrue(new File(zipFileName).delete());
	}

	@Test
	public void closeBeforeGetingFile() throws IOException {
		boolean exception = false;
		String zipFileName = testDir + "/empty.zip";
		ZipCompressor compressor = newCompressor(zipFileName);
		try {
			compressor.zipFile();
		}
		catch (IOException e) {
			exception = true;
			compressor.close();
			ZipFile zipFile = compressor.zipFile();
			assertEquals(0, zipFile.size());
			assertEquals(0, compressor.entries());
		}
		finally {
			assertTrue(new File(zipFileName).delete());
			assertTrue(exception);
		}
	}

	@Test
	public void addingEntriesFromInputStream() throws IOException {
		String zipFileName = testDir + "/test-zip-compressor-input-stream.zip";
		ZipCompressor compressor = newCompressor(zipFileName);

		String message = "TO-ZIP-ENTRY-FROM-INPUT-STREAM";
		InputStream stream = new ByteArrayInputStream(message.getBytes());
		compressor.add("simple/message", stream);
		compressor.close();

		ZipFile zipFile = compressor.zipFile();
		assertEquals(1, zipFile.size());

		ZipEntry entry = zipFile.getEntry("simple/message");
		assertFalse(entry == null);
		assertEquals(message.length(), entry.getSize());

		InputStream actualStream = zipFile.getInputStream(entry);

		byte[] read = new byte[message.length()];
		actualStream.read(read);
		assertEquals(message, new String(read));
		assertEquals(-1, actualStream.read());
		assertTrue(new File(zipFileName).delete());

		assertEquals(0, compressor.files());
		assertEquals(0, compressor.folders());
		assertEquals(1, compressor.entries());
	}

	@Test
	public void addingEntriesFromReader() throws IOException {
		String zipFileName = testDir + "/test-zip-compressor-reader.zip";
		ZipCompressor compressor = newCompressor(zipFileName);

		String message = "TO\nZIP\nENTRY\nFROM\nREADER";
		Reader reader = new CharArrayReader(message.toCharArray());
		compressor.add("simple/message", reader);
		compressor.close();

		ZipFile zipFile = compressor.zipFile();
		assertEquals(1, zipFile.size());

		int bytesWritten = message.length() + 1;
		ZipEntry entry = zipFile.getEntry("simple/message");
		assertFalse(entry == null);
		assertEquals(bytesWritten, entry.getSize());

		InputStream actualStream = zipFile.getInputStream(entry);

		byte[] read = new byte[bytesWritten];
		actualStream.read(read);
		assertEquals(-1, actualStream.read());
		assertTrue(new File(zipFileName).delete());

		char[] charArray = new String(read).toCharArray();
		BufferedReader buffered = new BufferedReader(new CharArrayReader(charArray));
		assertEquals("TO", buffered.readLine());
		assertEquals("ZIP", buffered.readLine());
		assertEquals("ENTRY", buffered.readLine());
		assertEquals("FROM", buffered.readLine());
		assertEquals("READER", buffered.readLine());
		assertTrue(null == buffered.readLine());

		assertEquals(0, compressor.files());
		assertEquals(0, compressor.folders());
		assertEquals(1, compressor.entries());
	}

	private ZipCompressor newCompressor(String zipFileName) {
		try {
			return new ZipCompressor(zipFileName);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private File resource(String resourceName) {
		URL resource = getClass().getResource(resourceName);
		return new File(resource.getFile());
	}
}
