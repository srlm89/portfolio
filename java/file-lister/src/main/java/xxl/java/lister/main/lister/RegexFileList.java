package xxl.java.lister.main.lister;

import static java.lang.String.format;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.PathMetadata;
import xxl.java.lister.metadata.FileMetadata;
import xxl.java.lister.metadata.visitor.*;

public class RegexFileList implements FileMetadataVisitor<Void> {

	public RegexFileList(PathMetadata metadata, String regex, String replacement) {
		this.regex = regex;
		this.replacement = replacement;
		this.fileList = new TreeMap<String, Integer>();
		metadata.accept(this);
	}

	@Override
	public Void visitSingleFile(FileMetadata file) {
		String path = file.filePath();
		if (path.matches(regex)) {
			register(path.replaceFirst(regex, replacement));
		}
		return null;
	}

	@Override
	public Void visitDirectory(DirectoryMetadata directory) {
		for (DirectoryMetadata subDirectory : directory.subDirectories()) {
			visitDirectory(subDirectory);
		}
		for (FileMetadata file : directory.files()) {
			visitSingleFile(file);
		}
		return null;
	}

	protected Map<String, Integer> fileList() {
		return fileList;
	}

	public void print(Writer writer) throws IOException {
		BufferedWriter buffered = new BufferedWriter(writer);
		for (String key : fileList.keySet()) {
			buffered.write(format("%s: %d", key, fileList.get(key)));
			buffered.newLine();
		}
		buffered.close();
	}

	private void register(String path) {
		if (!fileList.containsKey(path)) {
			fileList.put(path, 0);
		}
		fileList.put(path, fileList.get(path) + 1);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	private String regex;
	private String replacement;
	private Map<String, Integer> fileList;
}
