package droidcon.gadgetstop.service;

public interface ResponseCallback<T> extends ResponseDeserializer<T>{
    void onSuccess(T response);
    void onError(Exception exception);
}
