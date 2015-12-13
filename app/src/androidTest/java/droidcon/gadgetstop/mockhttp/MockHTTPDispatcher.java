package droidcon.gadgetstop.mockhttp;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import java.util.HashMap;
import java.util.Map;

public class MockHTTPDispatcher extends Dispatcher {

  private Map<MockRequest, MockResponse> requestResponseMap;

  public MockHTTPDispatcher() {
    requestResponseMap = new HashMap<>();
  }

  public MockHTTPDispatcher mock(MockRequest mockRequest) {
    MockResponse mockResponse = new MockResponse();
    mockResponse.setBody(mockRequest.response);
    requestResponseMap.put(mockRequest, mockResponse);
    return this;
  }

  @Override
  public MockResponse dispatch(RecordedRequest recordedRequest) {
    synchronized (this) {
      for (MockRequest mockRequest : requestResponseMap.keySet()) {
        if (mockRequest.path.contains(recordedRequest.getPath()))
          return requestResponseMap.get(mockRequest);
      }
      return new MockResponse().setResponseCode(404);
    }
  }
}