package droidcon.gadgetstop.rule;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import droidcon.gadgetstop.mockhttp.MockHTTPDispatcher;
import droidcon.gadgetstop.mockhttp.MockRequest;
import droidcon.gadgetstop.service.EnvironmentManager;
import okio.Buffer;


public class MockWebServerRule implements TestRule {

  private static final String DOMAIN = "localhost";
  private static final int PORT = 4568;
  private MockHTTPDispatcher mockHTTPDispatcher;
  private MockWebServer mockWebServer;

  public MockWebServerRule() {
    mockWebServer = new MockWebServer();
  }

  @Override
  public Statement apply(Statement statement, Description description) {
    return new MockHTTPServerStatement(statement);
  }

  public MockRequest mockResponse(String path, String httpMethod, String response) throws IOException {
    return mockResponse(path, httpMethod, new ByteArrayInputStream(response.getBytes()));
  }

  public MockRequest mockResponse(String path, String httpMethod, InputStream response) throws IOException {
    MockRequest mockRequest = new MockRequest(domain().concat(path), httpMethod, new Buffer().readFrom(response));
    mockHTTPDispatcher.mock(mockRequest);
    return mockRequest;
  }

  private String domain() {
    return String.format("http://%s:%s", DOMAIN, PORT);
  }

  private class MockHTTPServerStatement extends Statement {

    private Statement base;

    public MockHTTPServerStatement(Statement base) {
      this.base = base;
    }

    @Override
    public void evaluate() throws Throwable {
      EnvironmentManager.getInstance().switchEnvironmentTo(domain());
      mockHTTPDispatcher = new MockHTTPDispatcher();
      mockWebServer.setDispatcher(mockHTTPDispatcher);
      mockWebServer.start(PORT);
      try {
        this.base.evaluate();
      } finally {
        mockWebServer.shutdown();
      }
    }
  }
}
