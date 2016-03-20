package xxl.java.lister.model;


public interface FileMetadataVisitor<T> {

	T visitSingleFile(SingleFileMetadata file);

	T visitDirectory(DirectoryMetadata directory);
}
