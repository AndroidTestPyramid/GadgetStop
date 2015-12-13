package droidcon.gadgetstop.login.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.login.service.LoginService;
import droidcon.gadgetstop.login.view.LoginView;
import droidcon.gadgetstop.service.ResponseCallback;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

  private LoginPresenter loginPresenter;
  private LoginView loginView;
  private LoginService loginService;

  @Before
  public void setup() {
    loginView = mock(LoginView.class);
    loginService = mock(LoginService.class);
    loginPresenter = new LoginPresenter(loginView, loginService);
  }

  @Test
  public void shouldShowErrorForEmptyEmail() {
    loginPresenter.login("", null);

    verify(loginView).showErrorOnInvalidEmail(R.string.error_field_required);
  }

  @Test
  public void shouldShowErrorForNullEmail() {
    loginPresenter.login(null, null);

    verify(loginView).showErrorOnInvalidEmail(R.string.error_field_required);
  }

  @Test
  public void shouldShowErrorForInvalidEmail() {
    loginPresenter.login("abc", null);

    verify(loginView).showErrorOnInvalidEmail(R.string.error_invalid_email);
  }

  @Test
  public void shouldShowErrorForEmptyPassword() {
    loginPresenter.login("v@v", "");

    verify(loginView).showErrorOnInvalidPassword(R.string.error_field_required);
  }

  @Test
  public void shouldShowErrorForNullPassword() {
    loginPresenter.login("v@v", null);

    verify(loginView).showErrorOnInvalidPassword(R.string.error_field_required);
  }

  @Test
  public void shouldShowErrorForShortPassword() {
    loginPresenter.login("v@v", "1234");

    verify(loginView).showErrorOnInvalidPassword(R.string.error_invalid_password);
  }

  @Test
  public void shouldShowErrorForInvalidCredential() {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        ResponseCallback responseCallback = (ResponseCallback) invocation.getArguments()[2];
        responseCallback.onError(new Exception());
        return null;
      }
    }).when(loginService).doLogin(eq("v@v"), eq("12345"), any(ResponseCallback.class));
    loginPresenter.login("v@v", "12345");

    loginView.showErrorOnInvalidCredential(R.string.invalid_credential);
  }

  @Test
  public void shouldNavigateToShoppingActivityOnValidCredential() {
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        ResponseCallback responseCallback = (ResponseCallback) invocation.getArguments()[2];
        responseCallback.onSuccess("");
        return null;
      }
    }).when(loginService).doLogin(eq("v@v"), eq("12345"), any(ResponseCallback.class));
    loginPresenter.login("v@v", "12345");

    loginView.navigateToShoppingActivity();
  }
}