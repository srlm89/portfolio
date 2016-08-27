package xxl.java.lister.metadata;

import static java.lang.String.format;

import java.util.LinkedList;

import xxl.java.lister.metadata.visitor.FileMetadataVisitor;

/**
 * A {@code PathMetadata} represents metadata of a File System resource: a single file or a
 * directory. Immediate subclasses are: {@link FileMetadata} and {@link DirectoryMetadata}
 */
public abstract class PathMetadata implements Comparable<PathMetadata> {

	public abstract boolean isSingleFile();

	public abstract boolean isDirectory();

	public abstract void accept(FileMetadataVisitor<?> visitor);

	public static boolean existsIn(Iterable<? extends PathMetadata> iterable, String fileName) {
		return metadataFor(fileName, iterable) != null;
	}

	public static PathMetadata metadataFor(String fileName, Iterable<? extends PathMetadata> iterable) {
		for (PathMetadata file : iterable) {
			if (file.name().equals(fileName)) {
				return file;
			}
		}
		return null;
	}

	protected PathMetadata(String name, DirectoryMetadata parent) {
		this.name = name;
		this.parent = parent;
	}

	@Override
	public int compareTo(PathMetadata file) {
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
		Iterable<PathMetadata> road = roadFromRoot();
		for (PathMetadata file : road) {
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

	protected Iterable<PathMetadata> roadFromRoot() {
		LinkedList<PathMetadata> road = new LinkedList<PathMetadata>();
		PathMetadata file = this;
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
		if (object == null || !PathMetadata.class.isInstance(object)) {
			return false;
		}
		PathMetadata fileMetadata = (PathMetadata) object;
		return filePath().equals(fileMetadata.filePath());
	}

	private String name;
	private DirectoryMetadata parent;
}
