package droidcon.gadgetstop.service;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class APIClient extends AsyncTask<Object, Void, Object> {

  private ResponseCallback responseCallback;
  private RequestType requestType;

  protected Object doInBackground(Object... params) {
    this.requestType = (RequestType) params[0];
    this.responseCallback = (ResponseCallback) params[2];
    return response((String) params[1]);
  }

  private Object response(String url) {
    HttpClient httpClient = new DefaultHttpClient();
    HttpRequestBase httpRequest = httpRequest(url);
    Object result = null;
    try {
      HttpResponse httpResponse = httpClient.execute(httpRequest);
      StatusLine statusLine = httpResponse.getStatusLine();
      int statusCode = statusLine.getStatusCode();
      if (statusCode >= 200 && statusCode <= 210) {
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream content = httpEntity.getContent();
        result = responseCallback.deserialize(content);
      }
    } catch (IOException httpResponseError) {
      Log.e(this.getClass().getName(), "HTTP Error: " + httpResponseError.toString());
      return httpResponseError;
    }
    return result;
  }

  private HttpRequestBase httpRequest(String url) {
    if (requestType == RequestType.GET) return new HttpGet(url);
    else if (requestType == RequestType.POST) return new HttpPost(url);
    else return null;
  }


  @Override
  protected void onPostExecute(Object result) {
    if(result instanceof Exception){
      responseCallback.onError((Exception) result);
    }else {
      Log.d(this.getClass().getName(), "Content Data: " + result);
      responseCallback.onSuccess(result);
    }
  }

  public enum RequestType {
    GET,
    POST
  }
}