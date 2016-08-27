package xxl.java.lister.metadata.fs;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.FileMetadata;

public class FSDirectoryMetadata extends DirectoryMetadata {

	public FSDirectoryMetadata(File directory, DirectoryMetadata parentDirectory) {
		super(directoryName(directory), parentDirectory);
		this.directory = directory;
	}

	private static String directoryName(File directory) {
		String name = directory.getName();
		if (name.equals(".")) {
			try {
				name = new File(".").getCanonicalFile().getName();
			}
			catch (IOException e) {/**/}
		}
		return name;
	}

	@Override
	public Iterable<? extends FileMetadata> files() {
		File[] files = directory.listFiles(singleFileFilter);
		return new FSFileIterable(emptyIfNull(files), this);
	}

	@Override
	public Iterable<? extends DirectoryMetadata> subDirectories() {
		File[] subDirectories = directory.listFiles(directoryFilter);
		return new FSDirectoryIterable(emptyIfNull(subDirectories), this);
	}

	private File[] emptyIfNull(File[] array) {
		if (array == null) {
			array = new File[] {};
			log("Null array from directory: " + name());
		}
		return array;
	}

	private void log(String message) {
		System.err.println(message);
	}

	private File directory;

	private static FileFilter directoryFilter;
	private static FileFilter singleFileFilter;

	static {
		singleFileFilter = new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isFile();
			}
		};
		directoryFilter = new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
	}
}
