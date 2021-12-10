package ir.adrianet.uploaddownloadimage.Core;

public interface IService<U,T> {
    void SendRequest(U req);
    void OnSucceed(T result);
    void OnError(String error, int statusCode);
}
