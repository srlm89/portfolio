package xxl.java.lister.metadata.visitor;

import xxl.java.lister.metadata.DirectoryMetadata;
import xxl.java.lister.metadata.FileMetadata;


public interface FileMetadataVisitor<T> {

	T visitSingleFile(FileMetadata file);

	T visitDirectory(DirectoryMetadata directory);
}
