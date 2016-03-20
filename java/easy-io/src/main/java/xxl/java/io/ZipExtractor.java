package xxl.java.io;

import static java.lang.String.format;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipExtractor implements Closeable {

	public ZipExtractor(String zipFilePath) throws IOException {
		this.closed = false;
		this.zipFile = new ZipFile(zipFilePath);
	}

	public File extractTo(String destinationFolder) throws IOException {
		if (closed) {
			throw new IOException(format("%s instance already closed", ZipExtractor.class.getSimpleName()));
		}
		File destination = EasyFile.directoryCreateIfAbsent(destinationFolder);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				EasyFile.directoryCreateIfAbsent(destinationFolder, entry.getName());
			}
			else {
				extractTo(destination, entry);
			}
		}
		return destination;
	}

	private void extractTo(File destination, ZipEntry entry) throws IOException {
		int read = 0;
		byte[] data = new byte[BUFFER];
		File file = EasyFile.fileCreateIfAbsent(destination, entry.getName());
		OutputStream output = outputStream(file, data.length);
		InputStream input = new BufferedInputStream(zipFile.getInputStream(entry), data.length);
		while ((read = input.read(data)) != -1) {
			output.write(data, 0, read);
		}
		output.close();
		input.close();
	}

	private OutputStream outputStream(File file, int bufferSize) throws IOException {
		FileOutputStream inputStream = new FileOutputStream(file);
		return new BufferedOutputStream(inputStream, bufferSize);
	}

	@Override
	public void close() throws IOException {
		zipFile.close();
		closed = true;
	}

	private boolean closed;
	private ZipFile zipFile;

	private static final int BUFFER = 2048;
}
