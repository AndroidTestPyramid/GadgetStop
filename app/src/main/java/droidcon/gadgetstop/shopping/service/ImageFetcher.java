package droidcon.gadgetstop.shopping.service;

import android.graphics.Bitmap;

import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.ResponseCallback;

public class ImageFetcher {

  private final APIClient apiClient;

  public ImageFetcher(APIClient apiClient) {
    this.apiClient = apiClient;
  }

  public void execute(String imageUrl, ResponseCallback<Bitmap> bitmapResponseCallback) {
    apiClient.execute(APIClient.RequestType.GET, imageUrl, bitmapResponseCallback);
  }
}
