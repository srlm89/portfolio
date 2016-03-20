package xxl.java.lister.exe;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;

import xxl.java.lister.model.DirectoryMetadata;
import xxl.java.lister.model.SingleFileMetadata;

@SuppressWarnings("unchecked")
public class DirectoryDifference {

	public DirectoryDifference(DirectoryMetadata first, DirectoryMetadata second) {
		this.first = first;
		this.second = second;
	}

	public void showComparison(OutputStream output) throws IOException {
		this.output = output;
		LinkedList<DirectoryMetadata> leftStack = newLinkedList(first);
		LinkedList<DirectoryMetadata> rightStack = newLinkedList(second);
		compare(leftStack, rightStack);
		output.flush();
		this.output = null;
	}

	private void compare(LinkedList<DirectoryMetadata> leftStack, LinkedList<DirectoryMetadata> rightStack) {
		String path;
		String regex = format("^(/%s)(.*)$", first.name());
		while (!leftStack.isEmpty()) {
			DirectoryMetadata left = leftStack.pop();
			DirectoryMetadata right = rightStack.pop();
			Collection<String> leftSubDirectories = toCollection(left.subDirectoryNames());
			Collection<String> rightSubDirectories = toCollection(right.subDirectoryNames());
			Collection<String> subDirectoryIntersection = intersection(leftSubDirectories, rightSubDirectories);
			leftSubDirectories.removeAll(subDirectoryIntersection);
			rightSubDirectories.removeAll(subDirectoryIntersection);
			Collection<SingleFileMetadata> leftFiles = toCollection(left.files());
			Collection<SingleFileMetadata> rightFiles = toCollection(right.files());
			Collection<SingleFileMetadata> fileIntersection = intersection(leftFiles, rightFiles);
			leftFiles.removeAll(fileIntersection);
			rightFiles.removeAll(fileIntersection);
			path = left.filePath().replaceFirst(regex, ".$2");
			show(path, leftSubDirectories, rightSubDirectories, leftFiles, rightFiles);
			if (!subDirectoryIntersection.isEmpty()) {
				fill(leftStack, left, subDirectoryIntersection);
				fill(rightStack, right, subDirectoryIntersection);
			}
		}
	}

	private void show(String path, Collection<String> leftSubDirectories, Collection<String> rightSubDirectories,
			Collection<SingleFileMetadata> leftFiles, Collection<SingleFileMetadata> rightFiles) {
		int differences = leftSubDirectories.size() + rightSubDirectories.size() + leftFiles.size() + rightFiles.size();
		if (differences > 0) {
			write(path);
			showDifference("<<<  ", leftSubDirectories, "/");
			showDifference(">>>  ", rightSubDirectories, "/");
			showDifference("<--  ", leftFiles, "");
			showDifference("-->  ", rightFiles, "");
			write("");
		}
	}

	private void fill(LinkedList<DirectoryMetadata> stack, DirectoryMetadata directory, Collection<String> names) {
		Iterable<DirectoryMetadata> iterable = directory.subDirectoriesFor(names);
		stack.addAll(0, toCollection(iterable));
	}

	private <T> Collection<T> intersection(Collection<? extends T> left, Collection<? extends T> right) {
		Collection<T> inBoth = newLinkedList();
		inBoth.addAll(left);
		inBoth.retainAll(right);
		return inBoth;
	}

	private void showDifference(String prefix, Collection<? extends Object> objects, String suffix) {
		String line;
		for (Object object : objects) {
			if (SingleFileMetadata.class.isInstance(object)) {
				line = SingleFileMetadata.class.cast(object).printableName();
			}
			else {
				line = object.toString();
			}
			write(prefix + line + suffix);
		}
	}

	private void write(String line) {
		try {
			output.write(format("%s%n", line).getBytes());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> LinkedList<T> newLinkedList(T... elements) {
		return new LinkedList<T>(asList(elements));
	}

	private <T> Collection<T> toCollection(Iterable<? extends T> iterable) {
		Collection<T> collection = newLinkedList();
		for (T item : iterable) {
			collection.add(item);
		}
		return collection;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	private OutputStream output;
	private DirectoryMetadata first;
	private DirectoryMetadata second;
}
