package xxl.java.lister.index.zip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import xxl.java.io.EasyIO;
import xxl.java.lister.index.IndexedDirectory;
import xxl.java.lister.index.IndexedFile;
import xxl.java.lister.index.MetadataIndex;

public class ZipMetadataIndex extends MetadataIndex {

	public ZipMetadataIndex(ZipFile snapshot) {
		this.snapshot = snapshot;
		readDirectoryIndex();
	}

	@Override
	public IndexedDirectory root() {
		return buildFrom(0);
	}

	@Override
	public IndexedDirectory directory(int directoryId) {
		if (directoryId > -1) {
			return buildFrom(directoryId);
		}
		return null;
	}

	private void readDirectoryIndex() {
		String line;
		directoryIndex = new LinkedHashMap<Integer, Object[]>();
		ZipEntry indexEntry = snapshot.getEntry("index");
		try (InputStream stream = snapshot.getInputStream(indexEntry);
			 BufferedReader reader = EasyIO.asReader(stream)) {
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("/");
				int parentId = Integer.valueOf(parts[0]);
				int id = Integer.valueOf(parts[1]);
				int files = Integer.valueOf(parts[2]);
				int subDirectories = Integer.valueOf(parts[3]);
				String name = parts[4];
				directoryIndex.put(id, new Object[] { parentId, files, subDirectories, name });
			}
		}
		catch (IOException e) {
			throw new RuntimeException("Error reading 'index' entry from zip snapshot", e);
		}
	}
	
	private IndexedDirectory buildFrom(Integer directoryId) {
		String name = nameOf(directoryId);
		int files = numberOfFilesOf(directoryId);
		int subDirectories = numberOfSubdirectoriesOf(directoryId);
		int parentId = parentIdOf(directoryId);
		IndexedDirectory indexed = new IndexedDirectory(name, directory(parentId), this);
		if (files + subDirectories > 0) {
			ZipEntry dirSnapshot = snapshot.getEntry(directoryId.toString());
			fillIndexedDirectory(indexed, dirSnapshot, files, subDirectories);
		}
		return indexed;
	}

	private void fillIndexedDirectory(IndexedDirectory indexed, ZipEntry dirSnapshot, int files, int subDirectories) {
		try (InputStream stream = snapshot.getInputStream(dirSnapshot);
			 BufferedReader reader = EasyIO.asReader(stream)) {
			for (int i = 0; i < files; i += 1) {
				String[] sizeAndName = reader.readLine().split("/");
				long sizeInBytes = Long.valueOf(sizeAndName[0]);
				String fileName = sizeAndName[1];
				indexed.addSingleFile(new IndexedFile(fileName, indexed).withSize(sizeInBytes));
			}
			for (int i = 0; i < subDirectories; i += 1) {
				String[] idAndName = reader.readLine().split("/");
				int subDirectoryId = Integer.valueOf(idAndName[0]);
				String subDirName = idAndName[1];
				indexed.addSubDirectory(subDirName, subDirectoryId);
			}
		}
		catch (IOException e) {
			throw new RuntimeException("Error loading directory: " + indexed.name(), e);
		}
	}

	private int parentIdOf(int directoryId) {
		return (int) directoryIndex.get(directoryId)[0];
	}

	private int numberOfFilesOf(int directoryId) {
		return (int) directoryIndex.get(directoryId)[1];
	}

	private int numberOfSubdirectoriesOf(int directoryId) {
		return (int) directoryIndex.get(directoryId)[2];
	}

	private String nameOf(int directoryId) {
		return (String) directoryIndex.get(directoryId)[3];
	}

	private ZipFile snapshot;
	private Map<Integer, Object[]> directoryIndex;
}
