package xxl.java.lister.index;

import static java.lang.String.format;

import java.util.Collection;

import xxl.java.lister.model.DirectoryMetadata;

public abstract class DirectoryMetadataIndex {

	public abstract IndexedDirectoryMetadata root();

	public abstract IndexedDirectoryMetadata directory(int directoryId);

	public boolean hasSingleFile(String filePath) {
		String name = nameFrom(filePath);
		IndexedDirectoryMetadata parentDirectory = parentDirectoryOf(filePath);
		return parentDirectory != null && parentDirectory.hasSingleFile(name);
	}

	public boolean hasDirectory(String directoryPath) {
		if (directoryPath.equals("/")) {
			return true;
		}
		String name = nameFrom(directoryPath);
		IndexedDirectoryMetadata parentDirectory = parentDirectoryOf(directoryPath);
		return parentDirectory != null && parentDirectory.hasSubDirectory(name);
	}

	public IndexedDirectoryMetadata parentDirectoryOf(String filePath) {
		String parentPath = parentPath(filePath);
		if (!filePath.equals("/") && hasDirectory(parentPath)) {
			return bestDirectoryMatching(parentPath);
		}
		return null;
	}

	protected IndexedDirectoryMetadata bestDirectoryMatching(String directoryPath) {
		if (directoryPath.startsWith("/")) {
			directoryPath = directoryPath.substring(1);
		}
		String[] path = directoryPath.split("[/]");
		IndexedDirectoryMetadata indexedDirectory = root();
		for (String link : path) {
			if (!indexedDirectory.hasSubDirectory(link)) {
				break;
			}
			indexedDirectory = indexedDirectory.subDirectory(link);
		}
		return indexedDirectory;
	}

	protected Iterable<? extends DirectoryMetadata> directoryIterable(Collection<Integer> directories) {
		return new IndexedDirectoryMetadataIterable(directories, this);
	}

	protected String parentPath(String path) {
		String parent = path.replaceFirst("^/?(.*/)*([^/]+)$", "$1");
		if (parent.isEmpty()) {
			return "/";
		}
		return "/" + parent.substring(0, parent.lastIndexOf('/'));
	}

	protected String nameFrom(String path) {
		if (path.equals("/")) {
			return "";
		}
		return path.replaceFirst("^/?(.*/)*([^/]+)$", "$2");
	}

	@Override
	public String toString() {
		return format("%s [root=%s]", getClass().getSimpleName(), root());
	}
}
