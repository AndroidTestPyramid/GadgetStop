package droidcon.gadgetstop.service;

public class EnvironmentManager {

  private static EnvironmentManager instance;
  private String currentEnvironment;

  private EnvironmentManager(String currentEnvironment) {
    this.currentEnvironment = currentEnvironment;
  }

  public static EnvironmentManager getInstance() {
    if (instance == null) instance = new EnvironmentManager("http://xplorationstudio.com");
    return instance;
  }

  public String environment() {
    return currentEnvironment;
  }

  public void switchEnvironmentTo(String environment){
    currentEnvironment = environment;
  }
}
