package ir.adrianet.uploaddownloadimage.Core;


public interface IView<T> {
    void OnSucceed(T object);
    void OnError(String error, int statusCode);
}