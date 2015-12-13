package droidcon.gadgetstop.shopping.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageRepository {

  private static final String IMAGE_CACHING_PREFERENCES_FILE_NAME = "ImageCachingPreferences";

  private Context context;

  public ImageRepository(Context context) {
    this.context = context;
  }

  public void save(String imageUrl, Bitmap response) {
    String fileName = "image_" + System.currentTimeMillis() + ".png";
    try {
      ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
      response.compress(Bitmap.CompressFormat.PNG, 90, imageBytes);
      OutputStream imageOutputStream = outputFileStream(fileName);
      imageOutputStream.write(imageBytes.toByteArray());
      SharedPreferences cachingSharedPreferences = context.getSharedPreferences(IMAGE_CACHING_PREFERENCES_FILE_NAME, 0);
      SharedPreferences.Editor editor = cachingSharedPreferences.edit();

      editor.putString(imageUrl, fileName);
      editor.commit();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Bitmap getImageFor(String imageUrl) {
    SharedPreferences cachingSharedPreferences = context.getSharedPreferences(IMAGE_CACHING_PREFERENCES_FILE_NAME, 0);
    String fileName = cachingSharedPreferences.getString(imageUrl, "");
    Bitmap imageBitmap = null;
    try {
      imageBitmap = !fileName.isEmpty() ? BitmapFactory.decodeStream(inputFileStream(fileName)) : null;
    } catch (FileNotFoundException e) {
    }
    return imageBitmap;
  }

  private FileInputStream inputFileStream(String fileName) throws FileNotFoundException {
    if (isExternalStorageUsed()) {
      return new FileInputStream(context.getExternalCacheDir() + "/" + fileName);
    } else {
      return new FileInputStream(context.getCacheDir() + "/" + fileName);
    }
  }

  private FileOutputStream outputFileStream(String fileName) throws FileNotFoundException {
    if (isExternalStorageUsed())
      return new FileOutputStream(context.getExternalCacheDir() + "/" + fileName);
    else
      return new FileOutputStream(context.getCacheDir() + "/" + fileName);
  }

  private boolean isExternalStorageUsed() {
    SharedPreferences cachingSharedPreferences = context.getSharedPreferences(IMAGE_CACHING_PREFERENCES_FILE_NAME, 0);
    String isExternalMediaUsedKey = "isExternalMediaUsed";
    if (!cachingSharedPreferences.contains(isExternalMediaUsedKey)) {
      boolean mExternalStorageAvailable = false;
      boolean mExternalStorageWriteable = false;
      String state = Environment.getExternalStorageState();

      if (Environment.MEDIA_MOUNTED.equals(state)) {
        // We can read and write the media
        mExternalStorageAvailable = mExternalStorageWriteable = true;
      } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
        // We can only read the media
        mExternalStorageAvailable = true;
        mExternalStorageWriteable = false;
      } else {
        // Something else is wrong. It may be one of many other states, but all we need
        //  to know is we can neither read nor write
        mExternalStorageAvailable = mExternalStorageWriteable = false;
      }
      cachingSharedPreferences.edit().putBoolean(isExternalMediaUsedKey, mExternalStorageAvailable && mExternalStorageWriteable).commit();
    }
    return cachingSharedPreferences.getBoolean(isExternalMediaUsedKey, false);
  }
}

