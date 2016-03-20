package xxl.java.lister.index;

import java.util.*;

import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.SingleFileMetadata;

public class IndexedDirectoryMetadata extends DirectoryMetadata {

	public IndexedDirectoryMetadata(String name, DirectoryMetadata parent, DirectoryMetadataIndex metadataIndex) {
		super(name, parent);
		this.metadataIndex = metadataIndex;
	}

	protected Map<String, Integer> subDirectoryIndex() {
		if (directories == null) {
			setUpDirectories();
		}
		return directories;
	}

	protected Map<String, IndexedSingleFileMetadata> fileIndex() {
		if (files == null) {
			setUpFiles();
		}
		return files;
	}

	protected void setUpDirectories() {
		directories = new LinkedHashMap<String, Integer>();
	}

	protected void setUpFiles() {
		files = new LinkedHashMap<String, IndexedSingleFileMetadata>();
	}

	@Override
	public Iterable<? extends SingleFileMetadata> files() {
		return fileIndex().values();
	}

	@Override
	public Iterable<? extends DirectoryMetadata> subDirectories() {
		return metadataIndex.directoryIterable(subDirectoryIndex().values());
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
	public IndexedSingleFileMetadata singleFile(String fileName) {
		return fileIndex().get(fileName);
	}

	@Override
	public IndexedDirectoryMetadata subDirectory(String directoryName) {
		return metadataIndex.directory(subDirectoryIndex().get(directoryName));
	}

	public void addSingleFile(IndexedSingleFileMetadata singleFileMetadata) {
		fileIndex().put(singleFileMetadata.name(), singleFileMetadata);
	}

	public void addSubDirectory(String directoryName, int directoryId) {
		subDirectoryIndex().put(directoryName, directoryId);
	}

	private Map<String, Integer> directories;
	private Map<String, IndexedSingleFileMetadata> files;
	private DirectoryMetadataIndex metadataIndex;
}
