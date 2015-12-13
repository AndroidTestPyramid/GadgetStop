package droidcon.gadgetstop.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResponseDeserializerFactory {

  public static ResponseDeserializer<String> jsonStringDeserializer(){
    return new ResponseDeserializer<String>() {
      @Override
      public String deserialize(InputStream content) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        StringBuilder result = new StringBuilder();
        String line;
        try {
          while ((line = reader.readLine()) != null) {
            result.append(line);
          }
        } catch (IOException readerException) {
          readerException.printStackTrace();
        }
        return result.toString();
      }
    };
  }

  public static <T> ResponseDeserializer<T> objectDeserializer(final Type type){
    return new ResponseDeserializer<T>() {
      @Override
      public T deserialize(InputStream content) {
        String jsonString = jsonStringDeserializer().deserialize(content);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, type);
      }
    };
  }


  public static ResponseDeserializer<Bitmap> bitmapDeserializer() {
    return new ResponseDeserializer<Bitmap>() {
      @Override
      public Bitmap deserialize(InputStream content) {
        return BitmapFactory.decodeStream(content);
      }
    };
  }
}
