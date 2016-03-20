package xxl.java.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class ObjectMigrator {

	public static ObjectMigrator instance() {
		return instance;
	}

	private ObjectMigrator() {/**/}

	public <T extends Serializable> InputStream serialized(T object) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream serializer = new ObjectOutputStream(new BufferedOutputStream(outputStream));
		serializer.writeObject(object);
		serializer.close();
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T deserialized(InputStream stream) throws ClassNotFoundException, IOException {
		ObjectInputStream deserializer = new ObjectInputStream(stream);
		T object = (T) deserializer.readObject();
		deserializer.close();
		return object;
	}

	public void migrateTo(OutputStream destination, InputStream source) throws IOException {
		int read = 0;
		byte[] data = new byte[BUFFER_SIZE];
		InputStream bufferedIn = new BufferedInputStream(source);
		OutputStream bufferedOut = new BufferedOutputStream(destination);
		while ((read = bufferedIn.read(data)) != -1) {
			bufferedOut.write(data, 0, read);
		}
		bufferedIn.close();
		bufferedOut.close();
	}

	private static final ObjectMigrator instance;
	private static final int BUFFER_SIZE = 4096;

	static {
		instance = new ObjectMigrator();
	}
}
