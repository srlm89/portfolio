package xxl.java.lister.model;

import static java.lang.String.format;

import java.util.LinkedList;

/**
 * A {@code FileMetadata} represents metadata of a File System resource: a single file or a
 * directory. Concrete subclasses are: {@link SingleFileMetadata} and {@link DirectoryMetadata}
 */
public abstract class FileMetadata implements Comparable<FileMetadata> {

	public abstract boolean isSingleFile();

	public abstract boolean isDirectory();

	public abstract void accept(FileMetadataVisitor<?> visitor);

	public static boolean existsIn(Iterable<? extends FileMetadata> iterable, String fileName) {
		return metadataFor(fileName, iterable) != null;
	}

	public static FileMetadata metadataFor(String fileName, Iterable<? extends FileMetadata> iterable) {
		for (FileMetadata file : iterable) {
			if (file.name().equals(fileName)) {
				return file;
			}
		}
		return null;
	}

	protected FileMetadata(String name, DirectoryMetadata parent) {
		this.name = name;
		this.parent = parent;
	}

	@Override
	public int compareTo(FileMetadata file) {
		return filePath().compareTo(file.filePath());
	}

	public String name() {
		return name;
	}

	public String printableName() {
		return name();
	}

	public DirectoryMetadata parent() {
		return parent;
	}

	public boolean pathEquals(String filePath) {
		return filePath().equals(filePath);
	}

	public String filePath() {
		StringBuilder builder = new StringBuilder(128);
		Iterable<FileMetadata> road = roadFromRoot();
		for (FileMetadata file : road) {
			builder.append('/').append(file.name());
		}
		return builder.toString();
	}

	public String parentPath() {
		String parentPath = filePath().replaceFirst("(.*)/[^/]*", "$1");
		if (parentPath.isEmpty()) {
			return "/";
		}
		return parentPath;
	}

	protected Iterable<FileMetadata> roadFromRoot() {
		LinkedList<FileMetadata> road = new LinkedList<FileMetadata>();
		FileMetadata file = this;
		while (file != null) {
			road.push(file);
			file = file.parent();
		}
		return road;
	}

	@Override
	public String toString() {
		return format("%s(%s)", getClass().getSimpleName(), filePath());
	}

	@Override
	public int hashCode() {
		return filePath().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || !FileMetadata.class.isInstance(object)) {
			return false;
		}
		FileMetadata fileMetadata = (FileMetadata) object;
		return filePath().equals(fileMetadata.filePath());
	}

	private String name;
	private DirectoryMetadata parent;
}
