package xxl.java.lister.index.zip;

import static java.lang.String.format;

import java.io.InputStream;

public class StreamDirectoryArgs {

	public StreamDirectoryArgs() {/**/}

	public StreamDirectoryArgs files(int files) {
		this.files = files;
		return this;
	}

	public StreamDirectoryArgs subDirectories(int subDirectories) {
		this.subDirectories = subDirectories;
		return this;
	}

	public StreamDirectoryArgs parentId(int parentId) {
		this.parentId = parentId;
		return this;
	}

	public StreamDirectoryArgs name(String name) {
		this.name = name;
		return this;
	}

	public StreamDirectoryArgs stream(InputStream stream) {
		this.stream = stream;
		return this;
	}

	public boolean hasFiles() {
		return 0 < (files() + subDirectories());
	}

	public int files() {
		return files;
	}

	public int subDirectories() {
		return subDirectories;
	}

	public int parentId() {
		return parentId;
	}

	public String name() {
		return name;
	}

	public InputStream stream() {
		return stream;
	}

	@Override
	public String toString() {
		return format("%s(%s)", getClass().getSimpleName(), name());
	}

	private String name;
	private int files;
	private int parentId;
	private int subDirectories;
	private InputStream stream;
}
