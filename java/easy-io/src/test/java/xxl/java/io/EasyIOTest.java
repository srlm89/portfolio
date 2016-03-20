package xxl.java.io;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static xxl.java.io.EasyFile.fileCreateIfAbsent;
import static xxl.java.io.EasyIO.asBufferedReader;
import static xxl.java.io.EasyIO.asBufferedStream;
import static xxl.java.io.EasyIO.asBufferedWriter;
import static xxl.java.io.EasyIO.asInputStream;
import static xxl.java.io.EasyIO.asList;
import static xxl.java.io.EasyIO.asReader;
import static xxl.java.io.EasyIO.asSequence;
import static xxl.java.io.EasyIO.copyTo;
import static xxl.java.io.EasyIO.deserialized;
import static xxl.java.io.EasyIO.inputStreamFor;
import static xxl.java.io.EasyIO.newReader;
import static xxl.java.io.EasyIO.outputStreamFor;
import static xxl.java.io.EasyIO.readFromFile;
import static xxl.java.io.EasyIO.readerFor;
import static xxl.java.io.EasyIO.serialized;
import static xxl.java.io.EasyIO.writeToFile;
import static xxl.java.io.EasyIO.writerFor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EasyIOTest {

	private File testDir;
	
	@Before
	public void setUp() throws Exception {
		this.testDir = Files.createTempDirectory("_easy-io-test_").toFile();
	}
	
	@After
	public void tearDown() {
		EasyFile.delete(testDir);
	}
	
	@Test
	public void bufferedVersionOfStreams() throws IOException {
		File file = fileCreateIfAbsent(testDir, "test-io-1.txt");

		BufferedInputStream alreadyInputBuffer = new BufferedInputStream(new FileInputStream(file));
		assertTrue(alreadyInputBuffer == asBufferedStream(alreadyInputBuffer));

		InputStream inputStream = inputStreamFor(file);
		assertTrue(BufferedInputStream.class.isInstance(inputStream));

		BufferedOutputStream alreadyOutputBuffer = new BufferedOutputStream(new FileOutputStream(file));
		assertTrue(alreadyOutputBuffer == asBufferedStream(alreadyOutputBuffer));

		OutputStream outputStream = outputStreamFor(file);
		assertTrue(BufferedOutputStream.class.isInstance(outputStream));

		alreadyInputBuffer.close();
		inputStream.close();
		alreadyOutputBuffer.close();
		outputStream.close();
		assertTrue(file.delete());
	}

	@Test
	public void bufferedVersionOfReader() throws IOException {
		File file = fileCreateIfAbsent(testDir, "test-io-2.txt");

		BufferedReader alreadyBuffer = new BufferedReader(new FileReader(file));
		assertTrue(alreadyBuffer == asBufferedReader(alreadyBuffer));

		Reader reader = readerFor(file);
		assertTrue(BufferedReader.class.isInstance(reader));

		alreadyBuffer.close();
		reader.close();
		assertTrue(file.delete());
	}

	@Test
	public void bufferedVersionOfWriter() throws IOException {
		File file = fileCreateIfAbsent(testDir, "test-io-3.txt");

		BufferedWriter alreadyBuffer = new BufferedWriter(new FileWriter(file));
		assertTrue(alreadyBuffer == asBufferedWriter(alreadyBuffer));

		Writer writer = writerFor(file);
		assertTrue(BufferedWriter.class.isInstance(writer));

		alreadyBuffer.close();
		writer.close();
		assertTrue(file.delete());
	}

	@Test
	public void conversionStringAndStream() throws IOException {
		String message = "xxl.java!......!EasyIOTest!conversionStringAndStream";
		Collection<String> lines = Arrays.asList("xxl.java", "......", "EasyIOTest", "conversionStringAndStream");
		InputStream stream = asInputStream(lines, "!");
		String string = asSequence(stream);
		stream.close();
		assertEquals(string, message);
	}

	@Test
	public void conversionStringAndReader() throws IOException {
		Collection<String> lines = Arrays.asList("xxl.java", "......", "EasyIOTest", "conversionStringAndReader");
		Reader reader = asReader(lines);
		List<String> list = asList(reader);
		reader.close();
		assertEquals(lines, list);
	}

	@Test
	public void conversionStreamAndReader() throws IOException {
		String message = "xxl.java!......!EasyIOTest!conversionStreamAndReader";
		Collection<String> lines = Arrays.asList("xxl.java", "......", "EasyIOTest", "conversionStreamAndReader");
		InputStream stream = asInputStream(lines, "!");
		Reader reader = asReader(stream);
		List<String> list = asList(reader);
		reader.close();
		stream.close();
		assertEquals(1, list.size());
		assertEquals(message, list.get(0));
	}

	@Test
	public void conversionBytesToReader() throws IOException {
		String message = format("xxl.java%n......%nEasyIOTest%nconversionStreamAndReader2");
		BufferedReader reader = newReader(message.getBytes());
		assertEquals("xxl.java", reader.readLine());
		assertEquals("......", reader.readLine());
		assertEquals("EasyIOTest", reader.readLine());
		assertEquals("conversionStreamAndReader2", reader.readLine());
		assertEquals(null, reader.readLine());
		reader.close();
	}

	@Test
	public void readingAndWritingFile() throws IOException {
		File newFile = fileCreateIfAbsent(testDir, "test-io-4.txt");
		Collection<String> lines = Arrays.asList("xxl.java", "......", "EasyIOTest", "readingAndWritingFile");
		writeToFile(lines, newFile);
		List<String> read = readFromFile(newFile);
		assertEquals(lines, read);
		assertTrue(newFile.delete());
	}

	@Test
	public void readingAndWritingFile2() throws IOException {
		File newFile = fileCreateIfAbsent(testDir, "test-io-5.txt");
		String message = "xxl.java#......#EasyIOTest#readingAndWritingFile2";
		Collection<String> lines = Arrays.asList("xxl.java", "......", "EasyIOTest", "readingAndWritingFile2");
		OutputStream stream = outputStreamFor(newFile);
		copyTo(stream, lines, "#");
		List<String> read = readFromFile(newFile);
		stream.close();
		assertEquals(1, read.size());
		assertEquals(message, read.get(0));
		assertTrue(newFile.delete());
	}

	@Test
	public void readingAndWritingFile3() throws IOException {
		File newFile = fileCreateIfAbsent(testDir, "test-io-6.txt");
		Collection<String> lines = Arrays.asList("xxl.java", "......", "EasyIOTest", "readingAndWritingFile3");
		Reader reader = asReader(lines);
		BufferedWriter writer = writerFor(newFile);
		copyTo(writer, reader);
		BufferedReader fileReader = readerFor(newFile);
		List<String> read = asList(fileReader);
		reader.close();
		writer.close();
		fileReader.close();
		assertEquals(lines, read);
		assertTrue(newFile.delete());
	}

	@Test
	public void dumpingSerializable() throws Exception {
		Collection<Long[]> object = new LinkedList<Long[]>();
		object.add(new Long[] { 1L, 30L });
		object.add(new Long[] { null, 20L });
		Serializable serializable = (Serializable) object;
		InputStream dumped = serialized(serializable);
		List<Long[]> deserialized = deserialized(dumped);
		assertEquals(2, deserialized.size());
		assertEquals(2, deserialized.get(0).length);
		assertEquals(2, deserialized.get(1).length);
		assertTrue(1L == deserialized.get(0)[0].longValue());
		assertTrue(30L == deserialized.get(0)[1].longValue());
		assertTrue(null == deserialized.get(1)[0]);
		assertTrue(20L == deserialized.get(1)[1]);
	}
}
