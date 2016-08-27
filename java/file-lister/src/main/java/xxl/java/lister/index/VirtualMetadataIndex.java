package xxl.java.lister.index;

import static java.lang.String.format;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class VirtualMetadataIndex extends MetadataIndex {

	public VirtualMetadataIndex() {
		this.indexedDirectories = new LinkedHashMap<Integer, IndexedDirectory>();
		indexedDirectories.put(0, new IndexedDirectory("", null, this));
	}

	public IndexedFile singleFileAddIfAbsent(String filePath) {
		String name = super.nameFrom(filePath);
		String parentPath = super.parentPath(filePath);
		IndexedDirectory parentDirectory = directoryAddIfAbsent(parentPath);
		if (!parentDirectory.hasSingleFile(name)) {
			parentDirectory.addSingleFile(new IndexedFile(name, parentDirectory));
		}
		return parentDirectory.singleFile(name);
	}

	public IndexedDirectory directoryAddIfAbsent(String directoryPath) {
		IndexedDirectory directory = bestDirectoryMatching(directoryPath);
		if (!directory.pathEquals(directoryPath)) {
			directoryPath = directoryPath.replaceFirst(format("^/?(%s)/?(.+)$", directory.filePath()), "$2");
			String[] path = directoryPath.split("[/]");
			for (String link : path) {
				directory = createDirectory(directory, link);
			}
		}
		return directory;
	}

	private IndexedDirectory createDirectory(IndexedDirectory parent, String directoryName) {
		int id = newId();
		parent.addSubDirectory(directoryName, id);
		if (parent.equals(root())) {
			parent = null;
		}
		IndexedDirectory newDirectory = new IndexedDirectory(directoryName, parent, this);
		indexedDirectories.put(id, newDirectory);
		return newDirectory;
	}

	@Override
	public IndexedDirectory directory(int directoryId) {
		if (indexedDirectories.containsKey(directoryId)) {
			return indexedDirectories.get(directoryId);
		}
		throw new NoSuchElementException(format("Directory with id %d not found", directoryId));
	}

	@Override
	public IndexedDirectory root() {
		return indexedDirectories.get(0);
	}

	private int newId() {
		return indexedDirectories.size();
	}

	private LinkedHashMap<Integer, IndexedDirectory> indexedDirectories;
}
