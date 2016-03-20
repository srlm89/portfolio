package xxl.java.lister.model;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A {@code DirectoryMetadata} represents metadata for a directory in a file system.
 */
public abstract class DirectoryMetadata extends FileMetadata {

	public abstract Iterable<? extends SingleFileMetadata> files();

	public abstract Iterable<? extends DirectoryMetadata> subDirectories();

	protected DirectoryMetadata(String name, DirectoryMetadata parent) {
		super(name, parent);
	}

	public Iterable<String> subDirectoryNames() {
		Collection<String> names = new LinkedList<String>();
		for (DirectoryMetadata directory : subDirectories()) {
			names.add(directory.name());
		}
		return names;
	}

	public Iterable<DirectoryMetadata> subDirectoriesFor(Collection<String> subDirectoryNames) {
		Collection<DirectoryMetadata> subDirectories = new LinkedList<DirectoryMetadata>();
		for (DirectoryMetadata subDirectory : subDirectories()) {
			if (subDirectoryNames.contains(subDirectory.name())) {
				subDirectories.add(subDirectory);
			}
		}
		return subDirectories;
	}

	public boolean hasSingleFile(String fileName) {
		return existsIn(files(), fileName);
	}

	public boolean hasSubDirectory(String subDirectoryName) {
		return existsIn(subDirectories(), subDirectoryName);
	}

	public SingleFileMetadata singleFile(String fileName) {
		return (SingleFileMetadata) metadataFor(fileName, files());
	}

	public DirectoryMetadata subDirectory(String subDirectoryName) {
		return (DirectoryMetadata) metadataFor(subDirectoryName, subDirectories());
	}

	@Override
	public boolean isSingleFile() {
		return false;
	}

	@Override
	public boolean isDirectory() {
		return true;
	}

	@Override
	public void accept(FileMetadataVisitor<?> visitor) {
		visitor.visitDirectory(this);
	}

	@Override
	public int hashCode() {
		return 31 * name().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !DirectoryMetadata.class.isInstance(obj))
			return false;
		DirectoryMetadata other = (DirectoryMetadata) obj;
		return name().equals(other.name());
	}
}
