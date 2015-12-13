package droidcon.gadgetstop.shopping.service;

import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.EnvironmentManager;

public class ElectronicsFetcher extends ProductsFetcherService {

  public static final String ELECTRONICS_URL = "%s/sample_images/droidcon_electronics.json";

  public ElectronicsFetcher(APIClient apiClient) {
    super(String.format(ELECTRONICS_URL, EnvironmentManager.getInstance().environment()), apiClient);
  }
}
