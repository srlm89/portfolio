package xxl.java.lister.index.zip;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import xxl.java.io.EasyIO;
import xxl.java.lister.index.DirectoryMetadataIndex;
import xxl.java.lister.index.IndexedDirectoryMetadata;

public class ZipDirectoryMetadataIndex extends DirectoryMetadataIndex {

	public ZipDirectoryMetadataIndex(ZipFile snapshot) {
		this.snapshot = snapshot;
		setUpDirectoryIndex();
	}

	@Override
	public IndexedDirectoryMetadata root() {
		return buildFrom(0);
	}

	@Override
	public IndexedDirectoryMetadata directory(int directoryId) {
		if (directoryId > -1) {
			return buildFrom(directoryId);
		}
		return null;
	}

	private IndexedDirectoryMetadata buildFrom(Integer directoryId) {
		try {
			StreamDirectoryArgs args = new StreamDirectoryArgs();
			args.name(nameOf(directoryId));
			args.parentId(parentIdOf(directoryId));
			args.files(numberOfFilesOf(directoryId));
			args.subDirectories(numberOfSubdirectoriesOf(directoryId));
			if (args.hasFiles()) {
				ZipEntry directorySnapshot = snapshot.getEntry(directoryId.toString());
				args.stream(snapshot.getInputStream(directorySnapshot));
			}
			return new StreamDirectoryMetadata(args, this);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setUpDirectoryIndex() {
		try {
			String line;
			directoryIndex = new LinkedHashMap<Integer, Object[]>();
			ZipEntry indexEntry = snapshot.getEntry("index");
			BufferedReader reader = EasyIO.asReader(snapshot.getInputStream(indexEntry));
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
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected int parentIdOf(int directoryId) {
		return (int) directoryIndex.get(directoryId)[0];
	}

	protected int numberOfFilesOf(int directoryId) {
		return (int) directoryIndex.get(directoryId)[1];
	}

	protected int numberOfSubdirectoriesOf(int directoryId) {
		return (int) directoryIndex.get(directoryId)[2];
	}

	protected String nameOf(int directoryId) {
		return (String) directoryIndex.get(directoryId)[3];
	}

	private ZipFile snapshot;
	private Map<Integer, Object[]> directoryIndex;
}
