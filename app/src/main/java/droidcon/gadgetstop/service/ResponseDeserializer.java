package droidcon.gadgetstop.service;

import java.io.InputStream;

public interface ResponseDeserializer<T> {
  T deserialize(InputStream content);
}
