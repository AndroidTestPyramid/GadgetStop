package droidcon.gadgetstop.shopping.view;

import android.graphics.Bitmap;
import android.widget.ImageView;

import droidcon.gadgetstop.service.APIClient;
import droidcon.gadgetstop.shopping.presenter.ProductImagePresenter;
import droidcon.gadgetstop.shopping.repository.ImageRepository;
import droidcon.gadgetstop.shopping.service.ImageFetcher;

public class ProductImageViewControl implements ProductImageView{
  private final ProductImagePresenter productImagePresenter;
  private ImageView imageView;

  public ProductImageViewControl(ImageView imageView) {
    this.imageView = imageView;
    productImagePresenter = new ProductImagePresenter(new ImageFetcher(new APIClient()), new ImageRepository(imageView.getContext()), this);
  }

  public void renderImage(String imageUrl) {
    productImagePresenter.fetchImageFor(imageUrl);
  }

  public void renderImage(Bitmap image) {
    imageView.setImageBitmap(image);
  }
}
