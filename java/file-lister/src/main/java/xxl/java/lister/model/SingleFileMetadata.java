package xxl.java.lister.model;

import static java.lang.String.format;

/**
 * A {@code SingleFileMetadata} represents metadata for a single file in a file system.
 */
public abstract class SingleFileMetadata extends FileMetadata {

	public abstract long sizeInBytes();

	protected SingleFileMetadata(String name, DirectoryMetadata parent) {
		super(name, parent);
	}

	@Override
	public boolean isSingleFile() {
		return true;
	}

	@Override
	public boolean isDirectory() {
		return false;
	}

	@Override
	public void accept(FileMetadataVisitor<?> visitor) {
		visitor.visitSingleFile(this);
	}

	@Override
	public String printableName() {
		return format("%s (%d)", name(), sizeInBytes());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = name().hashCode();
		result = prime * result + (int) (sizeInBytes() ^ (sizeInBytes() >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || !SingleFileMetadata.class.isInstance(obj))
			return false;
		SingleFileMetadata other = (SingleFileMetadata) obj;
		return name().equals(other.name()) && (sizeInBytes() == other.sizeInBytes());
	}
}
