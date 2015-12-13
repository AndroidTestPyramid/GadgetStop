package droidcon.gadgetstop.shopping.service;

import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.EnvironmentManager;

public class AccessoriesFetcher extends ProductsFetcherService {

  public static final String ACCESSORIES_URL = "%s/sample_images/droidcon_accessories.json";

  public AccessoriesFetcher(APIClient apiClient) {
    super(String.format(ACCESSORIES_URL, EnvironmentManager.getInstance().environment()), apiClient);
  }
}
