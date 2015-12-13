package droidcon.gadgetstop.login.service;

import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.service.APIClient.RequestType;
import droidcon.gadgetstop.service.EnvironmentManager;
import droidcon.gadgetstop.service.ResponseCallback;

public class LoginService {

  private static final String LOGIN_URL = "%s/sample_images/%s/%s.json";

  private APIClient apiClient;

  public LoginService(APIClient apiClient){
    this.apiClient = apiClient;
  }

  public void doLogin(String email, String password, ResponseCallback responseCallback) {
    apiClient.execute(RequestType.GET, String.format(LOGIN_URL, EnvironmentManager.getInstance().environment(), email, password), responseCallback);
  }
}
