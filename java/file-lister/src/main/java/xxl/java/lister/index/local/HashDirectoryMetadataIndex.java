package xxl.java.lister.index.local;

import static java.lang.String.format;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import xxl.java.lister.index.DirectoryMetadataIndex;
import xxl.java.lister.index.IndexedDirectoryMetadata;
import xxl.java.lister.index.IndexedSingleFileMetadata;

public class HashDirectoryMetadataIndex extends DirectoryMetadataIndex {

	public HashDirectoryMetadataIndex() {
		this.indexedDirectories = new LinkedHashMap<Integer, IndexedDirectoryMetadata>();
		indexedDirectories.put(0, new IndexedDirectoryMetadata("", null, this));
	}

	public IndexedSingleFileMetadata singleFileAddIfAbsent(String filePath) {
		String name = super.nameFrom(filePath);
		String parentPath = super.parentPath(filePath);
		IndexedDirectoryMetadata parentDirectory = directoryAddIfAbsent(parentPath);
		if (!parentDirectory.hasSingleFile(name)) {
			parentDirectory.addSingleFile(new IndexedSingleFileMetadata(name, parentDirectory));
		}
		return parentDirectory.singleFile(name);
	}

	public IndexedDirectoryMetadata directoryAddIfAbsent(String directoryPath) {
		IndexedDirectoryMetadata directory = bestDirectoryMatching(directoryPath);
		if (!directory.pathEquals(directoryPath)) {
			directoryPath = directoryPath.replaceFirst(format("^/?(%s)/?(.+)$", directory.filePath()), "$2");
			String[] path = directoryPath.split("[/]");
			for (String link : path) {
				directory = createDirectory(directory, link);
			}
		}
		return directory;
	}

	private IndexedDirectoryMetadata createDirectory(IndexedDirectoryMetadata parent, String directoryName) {
		int id = newId();
		parent.addSubDirectory(directoryName, id);
		if (parent.equals(root())) {
			parent = null;
		}
		IndexedDirectoryMetadata newDirectory = new IndexedDirectoryMetadata(directoryName, parent, this);
		indexedDirectories.put(id, newDirectory);
		return newDirectory;
	}

	@Override
	public IndexedDirectoryMetadata directory(int directoryId) {
		if (indexedDirectories.containsKey(directoryId)) {
			return indexedDirectories.get(directoryId);
		}
		throw new NoSuchElementException(format("Directory with id %d not found", directoryId));
	}

	@Override
	public IndexedDirectoryMetadata root() {
		return indexedDirectories.get(0);
	}

	private int newId() {
		return indexedDirectories.size();
	}

	private LinkedHashMap<Integer, IndexedDirectoryMetadata> indexedDirectories;
}
