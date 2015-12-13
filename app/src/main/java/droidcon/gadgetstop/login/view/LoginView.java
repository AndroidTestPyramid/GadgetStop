package droidcon.gadgetstop.login.view;

public interface LoginView {

  void showErrorOnInvalidEmail(int errorMessage);

  void showErrorOnInvalidPassword(int errorMessage);

  void showProgressDialog(int message);

  void hideProgressDialog();

  void showErrorOnInvalidCredential(int errorMessage);

  void navigateToShoppingActivity();

  void showTechnicalDifficultyError(int technicalDifficulty);
}
