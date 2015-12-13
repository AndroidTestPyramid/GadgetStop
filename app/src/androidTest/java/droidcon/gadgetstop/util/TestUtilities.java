package droidcon.gadgetstop.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtilities {

  public static String readFrom(String assetFileName, Context context) throws IOException {
    InputStream inputStream = context.getAssets().open(assetFileName);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    String input = "";
    String line;
    while ((line = bufferedReader.readLine()) != null) {
      input += line;
    }
    return input;
  }

  public static InputStream readInputStreamFrom(String assetFileName, Context context) throws IOException {
    return context.getAssets().open(assetFileName);
  }
}