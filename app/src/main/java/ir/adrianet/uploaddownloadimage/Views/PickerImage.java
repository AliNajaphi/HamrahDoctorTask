package ir.adrianet.uploaddownloadimage.Views;

import android.graphics.Bitmap;

import java.io.File;

public interface PickerImage {
        void OnSelect();
        void onDone(byte[] bytesFile, Bitmap bitmap);
        void onError(String error);
}


