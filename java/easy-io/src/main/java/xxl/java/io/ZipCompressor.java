package xxl.java.io;

import static java.lang.String.format;

import java.io.*;
import java.util.zip.*;

public class ZipCompressor implements Closeable {

	/**
	 * Reference: http://www.oracle.com/technetwork/articles/java/compress-1565076.html
	 */

	public ZipCompressor(String zipFileName) throws IOException {
		this.files = 0;
		this.folders = 0;
		this.entries = 0;
		this.closed = false;
		this.fileName = zipFileName;
		this.zip = zipOutputStream(zipFileName);
		this.checksum = new CheckedOutputStream(zip, new Adler32());
	}

	public String fileName() {
		return fileName;
	}

	public int files() {
		return files;
	}

	public int folders() {
		return folders;
	}

	public int entries() {
		return entries;
	}

	public long checksum() {
		return checksum.getChecksum().getValue();
	}

	public ZipFile zipFile() throws IOException {
		if (closed) {
			return new ZipFile(fileName);
		}
		throw new IOException(format("Must close %s instance to open ZipFile", ZipCompressor.class.getSimpleName()));
	}

	public void add(File file) throws IOException {
		addEntry(file.getName(), file);
	}

	public void add(String entryName, InputStream stream) throws IOException {
		byte[] data = new byte[BUFFER];
		BufferedInputStream buffered = asBufferedInputStream(stream);
		ZipEntry entry = newEntry(entryName);
		zip.putNextEntry(entry);
		int read = 0;
		while ((read = buffered.read(data)) != -1) {
			checksum.write(data, 0, read);
		}
		buffered.close();
		zip.closeEntry();
	}

	public void add(String entryName, Reader reader) throws IOException {
		BufferedReader buffered = asBufferedReader(reader);
		ZipEntry entry = newEntry(entryName);
		zip.putNextEntry(entry);
		String read;
		while ((read = buffered.readLine()) != null) {
			checksum.write(read.getBytes());
			checksum.write(lineSeparator);
		}
		buffered.close();
		zip.closeEntry();
	}

	private void addEntry(String entryName, File file) throws IOException {
		if (file.isDirectory()) {
			addDirectory(entryName, file);
			folders += 1;
		}
		else {
			addSingleFile(entryName, file);
			files += 1;
		}
	}

	private ZipEntry newEntry(String entryName) {
		entries += 1;
		return new ZipEntry(entryName);
	}

	private void addDirectory(String entryName, File directory) throws IOException {
		for (File file : directory.listFiles()) {
			addEntry(entryName + File.separator + file.getName(), file);
		}
	}

	private void addSingleFile(String entryName, File file) throws IOException {
		add(entryName, new FileInputStream(file));
	}

	private ZipOutputStream zipOutputStream(String zipFileName) throws IOException {
		FileOutputStream stream = new FileOutputStream(zipFileName);
		BufferedOutputStream buffered = new BufferedOutputStream(stream);
		return new ZipOutputStream(buffered);
	}

	private BufferedInputStream asBufferedInputStream(InputStream stream) throws IOException {
		if (BufferedInputStream.class.isInstance(stream)) {
			return BufferedInputStream.class.cast(stream);
		}
		return new BufferedInputStream(stream, BUFFER);
	}

	private BufferedReader asBufferedReader(Reader reader) {
		if (BufferedReader.class.isInstance(reader)) {
			return BufferedReader.class.cast(reader);
		}
		return new BufferedReader(reader, BUFFER);
	}

	@Override
	public void close() throws IOException {
		zip.close();
		checksum.close();
		closed = true;
	}

	@Override
	public String toString() {
		return format("%s(%s) [entries=%d]", ZipCompressor.class.getSimpleName(), fileName(), entries);
	}

	private int files;
	private int folders;
	private int entries;
	private boolean closed;
	private String fileName;
	private ZipOutputStream zip;
	private CheckedOutputStream checksum;

	private static final int BUFFER = 4096;
	private static final byte[] lineSeparator = System.getProperty("line.separator").getBytes();
}
