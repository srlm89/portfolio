package xxl.java.lister.fs;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import xxl.java.lister.model.DirectoryMetadata;

public abstract class FileIterable<T> implements Iterable<T> {

	protected abstract T nextFrom(File file, DirectoryMetadata directoryMetadata);

	protected FileIterable(File[] files, DirectoryMetadata parentDirectory) {
		Arrays.sort(files);
		this.files = asList(files);
		this.parentDirectory = parentDirectory;
	}

	@Override
	public Iterator<T> iterator() {
		final Iterator<File> iterator = files.iterator();
		return new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public T next() {
				return nextFrom(iterator.next(), parentDirectory);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public String toString() {
		return format("%s(%s)", getClass().getSimpleName(), parentDirectory);
	}

	private List<File> files;
	private DirectoryMetadata parentDirectory;
}
