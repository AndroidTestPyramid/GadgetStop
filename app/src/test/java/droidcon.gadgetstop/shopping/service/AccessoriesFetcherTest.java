package droidcon.gadgetstop.shopping.service;

import org.junit.Test;

import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.APIClient.RequestType;
import droidcon.gadgetstop.service.ResponseCallback;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AccessoriesFetcherTest {

  public static final String ACCESSORIES_URL = "http://xplorationstudio.com/sample_images/droidcon_accessories.json";

  @Test
  public void shouldInvokeExecuteRequestWithAccessoriesUrl(){
    APIClient apiClientMock = mock(APIClient.class);

    AccessoriesFetcher accessoriesFetcher = new AccessoriesFetcher(apiClientMock);

    ResponseCallback responseCallback = mock(ResponseCallback.class);
    accessoriesFetcher.execute(responseCallback);

    verify(apiClientMock).execute(eq(RequestType.GET), eq(ACCESSORIES_URL), eq(responseCallback));
  }
}