package droidcon.gadgetstop.shopping.presenter;

import android.graphics.Bitmap;

import java.io.InputStream;

import droidcon.gadgetstop.service.ResponseCallback;
import droidcon.gadgetstop.service.ResponseDeserializerFactory;
import droidcon.gadgetstop.shopping.repository.ImageRepository;
import droidcon.gadgetstop.shopping.service.ImageFetcher;
import droidcon.gadgetstop.shopping.view.ProductImageView;
import droidcon.gadgetstop.shopping.view.ProductImageViewControl;

public class ProductImagePresenter {
  private final ImageFetcher imageFetcher;
  private ImageRepository imageRepository;
  private ProductImageView productImageView;

  public ProductImagePresenter(ImageFetcher imageFetcher, ImageRepository imageRepository, ProductImageView productImageView) {
    this.imageFetcher = imageFetcher;
    this.imageRepository = imageRepository;
    this.productImageView = productImageView;
  }

  public void fetchImageFor(String imageUrl) {
    Bitmap image = imageRepository.getImageFor(imageUrl);
    if (image != null)
      productImageView.renderImage(image);
    else
      imageFetcher.execute(imageUrl, bitmapCallback(imageUrl));
  }

  private ResponseCallback<Bitmap> bitmapCallback(final String imageUrl) {
    return new ResponseCallback<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream response) {
        return ResponseDeserializerFactory.bitmapDeserializer().deserialize(response);
      }

      @Override
      public void onSuccess(Bitmap response) {
        imageRepository.save(imageUrl, response);
        productImageView.renderImage(response);
      }

      @Override
      public void onError(Exception exception) {
      }
    };
  }
}
