package xxl.java.lister.index.zip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import xxl.java.io.EasyIO;
import xxl.java.lister.index.DirectoryMetadataIndex;
import xxl.java.lister.index.IndexedDirectoryMetadata;
import xxl.java.lister.index.IndexedSingleFileMetadata;

public class StreamDirectoryMetadata extends IndexedDirectoryMetadata {

	public StreamDirectoryMetadata(StreamDirectoryArgs args, DirectoryMetadataIndex metadataIndex) {
		super(args.name(), metadataIndex.directory(args.parentId()), metadataIndex);
		this.args = args;
	}

	@Override
	protected void setUpDirectories() {
		setUpWithZipEntry();
	}

	@Override
	protected void setUpFiles() {
		setUpWithZipEntry();
	}

	private void setUpWithZipEntry() {
		super.setUpFiles();
		super.setUpDirectories();
		if (args.hasFiles()) {
			try {
				InputStream stream = args.stream();
				BufferedReader reader = EasyIO.asReader(stream);
				loadSingleFiles(reader);
				loadSubDirectories(reader);
				stream.close();
			}
			catch (IOException e) {
				throw new RuntimeException("Error loading directory: " + name(), e);
			}
		}
	}

	private void loadSingleFiles(BufferedReader reader) throws IOException {
		for (int i = 0; i < args.files(); i += 1) {
			String[] singleFileSpec = reader.readLine().split("/");
			String name = singleFileSpec[1];
			long sizeInBytes = Long.valueOf(singleFileSpec[0]);
			IndexedSingleFileMetadata fileMetadata = new IndexedSingleFileMetadata(name, this).withSize(sizeInBytes);
			super.addSingleFile(fileMetadata);
		}
	}

	private void loadSubDirectories(BufferedReader reader) throws IOException {
		for (int i = 0; i < args.subDirectories(); i += 1) {
			String[] subDirectorySpec = reader.readLine().split("/");
			String name = subDirectorySpec[1];
			int subDirectoryId = Integer.valueOf(subDirectorySpec[0]);
			super.addSubDirectory(name, subDirectoryId);
		}
	}

	private StreamDirectoryArgs args;
}
