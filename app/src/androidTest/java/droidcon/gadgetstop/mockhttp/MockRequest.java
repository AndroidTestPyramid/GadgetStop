package droidcon.gadgetstop.mockhttp;

import okio.Buffer;

public class MockRequest {
  String path;
  String httpMethod;
  Buffer response;

  public MockRequest(String path, String httpMethod, Buffer response) {
    this.path = path;
    this.httpMethod = httpMethod;
    this.response = response;
  }
}
