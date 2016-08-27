package xxl.java.lister.metadata.fs;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class FSPathIterable<T> implements Iterable<T> {

	protected abstract T nextFrom(File file, FSDirectoryMetadata directoryMetadata);

	protected FSPathIterable(File[] files, FSDirectoryMetadata parentDirectory) {
		this.files = asList(files);
		this.parentDirectory = parentDirectory;
		Collections.sort(this.files);
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
	private FSDirectoryMetadata parentDirectory;
}
