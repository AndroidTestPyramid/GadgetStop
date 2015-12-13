package droidcon.gadgetstop.shopping.service;

import java.util.ArrayList;

import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.APIClient.RequestType;
import droidcon.gadgetstop.service.ResponseCallback;
import droidcon.gadgetstop.shopping.model.Product;

public class ProductsFetcherService {
  private final String request;
  private final APIClient apiClient;

  public ProductsFetcherService(String request, APIClient apiClient) {
    this.request = request;
    this.apiClient = apiClient;
  }

  public void execute(ResponseCallback<ArrayList<Product>> productsCallback) {
    apiClient.execute(RequestType.GET, request, productsCallback);
  }
}
