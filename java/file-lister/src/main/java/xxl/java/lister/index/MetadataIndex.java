package xxl.java.lister.index;

import static java.lang.String.format;

public abstract class MetadataIndex {

	public abstract IndexedDirectory root();

	public abstract IndexedDirectory directory(int directoryId);

	public boolean hasSingleFile(String filePath) {
		String name = nameFrom(filePath);
		IndexedDirectory parentDirectory = parentDirectoryOf(filePath);
		return parentDirectory != null && parentDirectory.hasSingleFile(name);
	}

	public boolean hasDirectory(String directoryPath) {
		if (directoryPath.equals("/")) {
			return true;
		}
		String name = nameFrom(directoryPath);
		IndexedDirectory parentDirectory = parentDirectoryOf(directoryPath);
		return parentDirectory != null && parentDirectory.hasSubDirectory(name);
	}

	public IndexedDirectory parentDirectoryOf(String filePath) {
		String parentPath = parentPath(filePath);
		if (!filePath.equals("/") && hasDirectory(parentPath)) {
			return bestDirectoryMatching(parentPath);
		}
		return null;
	}

	protected IndexedDirectory bestDirectoryMatching(String directoryPath) {
		if (directoryPath.startsWith("/")) {
			directoryPath = directoryPath.substring(1);
		}
		String[] path = directoryPath.split("[/]");
		IndexedDirectory indexedDirectory = root();
		for (String link : path) {
			if (!indexedDirectory.hasSubDirectory(link)) {
				break;
			}
			indexedDirectory = indexedDirectory.subDirectory(link);
		}
		return indexedDirectory;
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
