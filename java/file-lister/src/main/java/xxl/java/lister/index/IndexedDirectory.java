package xxl.java.lister.index;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.FileMetadata;

public class IndexedDirectory extends DirectoryMetadata {

	public IndexedDirectory(String name, IndexedDirectory parent, MetadataIndex metadataIndex) {
		super(name, parent);
		this.directories = new LinkedHashMap<String, Integer>();
		this.files = new LinkedHashMap<String, IndexedFile>();
		this.metadataIndex = metadataIndex;
	}

	protected Map<String, Integer> subDirectoryIndex() {
		return directories;
	}

	protected Map<String, IndexedFile> fileIndex() {
		return files;
	}

	@Override
	public Iterable<? extends FileMetadata> files() {
		return fileIndex().values();
	}

	@Override
	public Iterable<? extends DirectoryMetadata> subDirectories() {
		Collection<Integer> ids = subDirectoryIndex().values();
		return new IndexedDirectoryIterable(ids, metadataIndex);
	}

	@Override
	public Collection<String> subDirectoryNames() {
		return subDirectoryIndex().keySet();
	}

	@Override
	public Iterable<DirectoryMetadata> subDirectoriesFor(Collection<String> subDirectoryNames) {
		Collection<DirectoryMetadata> subDirectories = new LinkedList<DirectoryMetadata>();
		for (String subDirectoryName : subDirectoryNames) {
			if (hasSubDirectory(subDirectoryName)) {
				subDirectories.add(subDirectory(subDirectoryName));
			}
		}
		return subDirectories;
	}

	@Override
	public boolean hasSingleFile(String fileName) {
		return fileIndex().containsKey(fileName);
	}

	@Override
	public boolean hasSubDirectory(String directoryName) {
		return subDirectoryIndex().containsKey(directoryName);
	}

	@Override
	public IndexedFile singleFile(String fileName) {
		return fileIndex().get(fileName);
	}

	@Override
	public IndexedDirectory subDirectory(String directoryName) {
		return metadataIndex.directory(subDirectoryIndex().get(directoryName));
	}

	public void addSingleFile(IndexedFile singleFileMetadata) {
		fileIndex().put(singleFileMetadata.name(), singleFileMetadata);
	}

	public void addSubDirectory(String directoryName, int directoryId) {
		subDirectoryIndex().put(directoryName, directoryId);
	}

	private Map<String, Integer> directories;
	private Map<String, IndexedFile> files;
	private MetadataIndex metadataIndex;
}
