package xxl.java.io;

import static java.lang.System.lineSeparator;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EasyIO {

	public static BufferedReader asReader(InputStream stream) {
		return asBufferedReader(new InputStreamReader(stream));
	}

	public static BufferedWriter asWriter(OutputStream stream) {
		return asBufferedWriter(new OutputStreamWriter(stream));
	}

	public static BufferedReader newReader(char[] arrayToRead) {
		return asBufferedReader(new CharArrayReader(arrayToRead));
	}

	public static BufferedReader newReader(byte[] bytesToRead) {
		return asReader(newInputStream(bytesToRead));
	}

	public static CharArrayWriter newWriter() {
		return new CharArrayWriter();
	}

	public static BufferedReader readerFor(File file) throws IOException {
		return asBufferedReader(new FileReader(file));
	}

	public static BufferedWriter writerFor(File file) throws IOException {
		return asBufferedWriter(new FileWriter(file));
	}

	public static InputStream newInputStream(byte[] arrayToRead) {
		return asBufferedStream(new ByteArrayInputStream(arrayToRead));
	}

	public static ByteArrayOutputStream newOutputStream() {
		return new ByteArrayOutputStream();
	}

	public static InputStream inputStreamFor(File file) throws IOException {
		return asBufferedStream(new FileInputStream(file));
	}

	public static OutputStream outputStreamFor(File file) throws IOException {
		return asBufferedStream(new FileOutputStream(file));
	}

	public static InputStream asBufferedStream(InputStream stream) {
		return asBufferedStream(stream, BUFFER_SIZE);
	}

	public static OutputStream asBufferedStream(OutputStream stream) {
		return asBufferedStream(stream, BUFFER_SIZE);
	}

	public static BufferedReader asBufferedReader(Reader reader) {
		if (BufferedReader.class.isInstance(reader)) {
			return BufferedReader.class.cast(reader);
		}
		return new BufferedReader(reader);
	}

	public static BufferedWriter asBufferedWriter(Writer writer) {
		if (BufferedWriter.class.isInstance(writer)) {
			return BufferedWriter.class.cast(writer);
		}
		return new BufferedWriter(writer);
	}

	public static BufferedInputStream asBufferedStream(InputStream stream, int bufferSize) {
		if (BufferedInputStream.class.isInstance(stream)) {
			return BufferedInputStream.class.cast(stream);
		}
		return new BufferedInputStream(stream);
	}

	public static BufferedOutputStream asBufferedStream(OutputStream stream, int bufferSize) {
		if (BufferedOutputStream.class.isInstance(stream)) {
			return BufferedOutputStream.class.cast(stream);
		}
		return new BufferedOutputStream(stream);
	}

	public static List<String> asList(Reader reader) throws IOException {
		String line;
		List<String> lines = new LinkedList<String>();
		BufferedReader buffered = new BufferedReader(reader);
		while ((line = buffered.readLine()) != null) {
			lines.add(line);
		}
		buffered.close();
		return lines;
	}

	public static Reader asReader(Iterable<String> lines) throws IOException {
		CharArrayWriter writer = newWriter();
		BufferedWriter buffered = new BufferedWriter(writer);
		for (String line : lines) {
			buffered.write(line);
			buffered.newLine();
		}
		buffered.close();
		writer.close();
		return newReader(writer.toCharArray());
	}

	public static String asSequence(InputStream stream) throws IOException {
		StringBuilder builder = new StringBuilder();
		int read;
		byte[] data = new byte[BUFFER_SIZE];
		InputStream buffered = asBufferedStream(stream);
		while ((read = buffered.read(data)) != -1) {
			builder.append(new String(data, 0, read));
		}
		return builder.toString();
	}

	public static InputStream asInputStream(Iterable<String> lines) throws IOException {
		return asInputStream(lines, lineSeparator());
	}

	public static InputStream asInputStream(Iterable<String> lines, String lineSeparator) throws IOException {
		String afterLine = "";
		ByteArrayOutputStream output = newOutputStream();
		Iterator<String> iterator = lines.iterator();
		while (iterator.hasNext()) {
			output.write(afterLine.getBytes());
			output.write(iterator.next().getBytes());
			afterLine = lineSeparator;
		}
		output.close();
		return newInputStream(output.toByteArray());
	}

	public static List<String> readFromFile(File file) throws IOException {
		CharArrayWriter writer = newWriter();
		copyTo(writer, readerFor(file));
		writer.close();
		return asList(newReader(writer.toCharArray()));
	}

	public static void writeToFile(Iterable<String> lines, File file) throws IOException {
		Writer writer = writerFor(file);
		Reader reader = asReader(lines);
		copyTo(writer, reader);
		reader.close();
		writer.close();
	}

	public static void copyTo(Writer writer, Reader reader) throws IOException {
		String read = "";
		BufferedReader bufferedIn = new BufferedReader(reader);
		BufferedWriter bufferedOut = new BufferedWriter(writer);
		while ((read = bufferedIn.readLine()) != null) {
			bufferedOut.write(read);
			bufferedOut.newLine();
		}
		bufferedIn.close();
		bufferedOut.close();
	}

	public static void copyTo(OutputStream destination, Iterable<String> lines) throws IOException {
		copyTo(destination, lines, lineSeparator());
	}

	public static void copyTo(OutputStream destination, Iterable<String> lines, String lineSeparator) throws IOException {
		InputStream stream = asInputStream(lines, lineSeparator);
		copyTo(destination, stream);
		stream.close();
	}

	public static void copyTo(OutputStream destination, InputStream source) throws IOException {
		int read = 0;
		byte[] data = new byte[BUFFER_SIZE];
		InputStream bufferedIn = new BufferedInputStream(source);
		OutputStream bufferedOut = new BufferedOutputStream(destination);
		while ((read = bufferedIn.read(data)) != -1) {
			bufferedOut.write(data, 0, read);
		}
		bufferedIn.close();
		bufferedOut.close();
	}

	public static <T extends Serializable> InputStream serialized(T object) throws IOException {
		ByteArrayOutputStream outputStream = newOutputStream();
		OutputStream bufferedStream = asBufferedStream(outputStream);
		ObjectOutputStream serializer = new ObjectOutputStream(bufferedStream);
		serializer.writeObject(object);
		serializer.close();
		bufferedStream.close();
		return newInputStream(outputStream.toByteArray());
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserialized(InputStream stream) throws ClassNotFoundException, IOException {
		ObjectInputStream deserializer = new ObjectInputStream(stream);
		T object = (T) deserializer.readObject();
		deserializer.close();
		return object;
	}

	private static final int BUFFER_SIZE = 4096;
}
