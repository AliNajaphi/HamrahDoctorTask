package ir.adrianet.uploaddownloadimage.General;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import ir.adrianet.uploaddownloadimage.MainActivity;

public class TypeConvertor {

    public static String getStringBytes(byte[] bytes)
    {
        //return new String(bytes, Charset.forName("UTF-8"));
        return Base64.encodeToString(bytes, Base64.NO_WRAP | Base64.URL_SAFE);
    }

    public static byte[] getBytesString(String strUtf8)
    {
//        try {
//            return strUtf8.getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return new byte[]{};
//        }
        return Base64.decode(strUtf8, Base64.NO_WRAP | Base64.URL_SAFE);
    }
    public static byte[] getBytesBitmap(Bitmap bitmap)  {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmapUri(Uri uri) throws IOException {
        return MediaStore.Images.Media.getBitmap(MainActivity.getGlobal().getContentResolver(), uri);
    }
}
