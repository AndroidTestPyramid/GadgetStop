package droidcon.gadgetstop.shopping.presenter;

import android.graphics.Bitmap;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import droidcon.gadgetstop.service.ResponseCallback;
import droidcon.gadgetstop.shopping.repository.ImageRepository;
import droidcon.gadgetstop.shopping.service.ImageFetcher;
import droidcon.gadgetstop.shopping.view.ProductImageView;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductImagePresenterTest {

  private ImageFetcher imageFetcher;
  private Bitmap bitmap;
  private ProductImageView productImageView;
  private ImageRepository imageRepository;

  @Before
  public void setup(){
    imageFetcher = mock(ImageFetcher.class);
    bitmap = mock(Bitmap.class);
    productImageView = mock(ProductImageView.class);
    imageRepository = mock(ImageRepository.class);
  }

  @Test
  public void shouldInvokeRenderImageOnSuccessfullyFetchingImage(){
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[1];
        callback.onSuccess(bitmap);
        return null;
      }
    }).when(imageFetcher).execute(eq(""), Matchers.<ResponseCallback<Bitmap>>any());

    ProductImagePresenter productDetailsPresenter = new ProductImagePresenter(imageFetcher, imageRepository, productImageView);
    productDetailsPresenter.fetchImageFor("");

    verify(imageRepository).save("", bitmap);
    verify(productImageView).renderImage(bitmap);
  }

  @Test
  public void shouldInvokeRenderImageSuccessfullyAfterDeserialization(){
    final Bitmap[] bitmap = new Bitmap[1];

    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        final ResponseCallback callback = (ResponseCallback) invocation.getArguments()[1];
        final String fileToString = FileUtils.readFileToString(new File("src/test/resources/ic_launcher.png"));
        final InputStream byteArrayInputStream = new ByteArrayInputStream(fileToString.getBytes());
        bitmap[0] = (Bitmap) callback.deserialize(byteArrayInputStream);
        callback.onSuccess(bitmap[0]);
        return null;
      }
    }).when(imageFetcher).execute(eq(""), Matchers.<ResponseCallback<Bitmap>>any());

    ProductImagePresenter productDetailsPresenter = new ProductImagePresenter(imageFetcher, imageRepository, productImageView);
    productDetailsPresenter.fetchImageFor("");

    verify(productImageView).renderImage(bitmap[0]);
  }
}