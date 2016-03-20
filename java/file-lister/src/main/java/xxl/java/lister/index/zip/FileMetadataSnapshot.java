package xxl.java.lister.index.zip;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.zip.ZipFile;

import xxl.java.io.EasyIO;
import xxl.java.io.ZipCompressor;
import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.FileMetadataVisitor;
import xxl.java.lister.model.SingleFileMetadata;

public class FileMetadataSnapshot implements FileMetadataVisitor<String> {

	// @formatter:off
	/**
	* Specification of a directory entry:
	*
	*     Single files:   %d/%s (sizeInBytes/fileName)
	*     Subdirectories: %d/%s (directoryId/directoryName)
	*
	* Specification of 'index' entry:
	*
	*     %d/%d/%d/%d/%s (parentId/directoryId/files/subdirectories/directoryName)
	*
	* Root folder has id 0
	*/
	// @formatter:on

	public FileMetadataSnapshot(String snapshotFile, DirectoryMetadata metadata) throws IOException {
		this.compressor = new ZipCompressor(snapshotFile);
		this.directoryIndex = new HashMap<Integer, String>();
		this.parentId = -1;
		this.currentId = -1;
		metadata.accept(this);
		addIndexEntry();
		addVersionEntry();
		compressor.close();
	}

	public ZipFile snapshotFile() throws IOException {
		return compressor.zipFile();
	}

	@Override
	public String visitSingleFile(SingleFileMetadata file) {
		return format("%d/%s", file.sizeInBytes(), file.name());
	}

	@Override
	public String visitDirectory(DirectoryMetadata directory) {
		int id = newId();
		List<String> subDirectories = visitSubDirectories(directory, parentId(), id);
		List<String> files = visitSingleFiles(directory);
		addSnapshot(id, subDirectories, files);
		String name = directory.name();
		directoryIndex.put(id, format("%d/%d/%d/%d/%s", parentId(), id, files.size(), subDirectories.size(), name));
		return format("%d/%s", id, name);
	}

	private List<String> visitSubDirectories(DirectoryMetadata directory, int parentId, int newParentId) {
		setParentId(newParentId);
		List<String> collectedSubdirectories = new LinkedList<String>();
		for (DirectoryMetadata subDirectory : directory.subDirectories()) {
			collectedSubdirectories.add(visitDirectory(subDirectory));
		}
		setParentId(parentId);
		return collectedSubdirectories;
	}

	private List<String> visitSingleFiles(DirectoryMetadata directory) {
		List<String> collectedFiles = new LinkedList<String>();
		for (SingleFileMetadata singleFile : directory.files()) {
			collectedFiles.add(visitSingleFile(singleFile));
		}
		return collectedFiles;
	}

	private void addIndexEntry() {
		List<String> names = new ArrayList<String>(directoryIndex.size() + 1);
		for (Integer id : directoryIndex.keySet()) {
			names.add(directoryIndex.get(id));
		}
		Collections.sort(names);
		addEntry("index", names);
	}

	private void addVersionEntry() {
		addEntry("version", asList("1"));
	}

	private void addSnapshot(int id, List<String> subDirectories, List<String> files) {
		List<String> snapshot = new LinkedList<String>();
		snapshot.addAll(files);
		snapshot.addAll(subDirectories);
		addEntry(String.valueOf(id), snapshot);
	}

	private void addEntry(String entryName, List<String> lines) {
		if (!lines.isEmpty()) {
			try {
				Reader reader = EasyIO.asReader(lines);
				compressor.add(entryName, reader);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void setParentId(int id) {
		parentId = id;
	}

	private int parentId() {
		return parentId;
	}

	private int newId() {
		currentId += 1;
		return currentId;
	}

	private int parentId;
	private int currentId;
	private ZipCompressor compressor;
	private Map<Integer, String> directoryIndex;
}
