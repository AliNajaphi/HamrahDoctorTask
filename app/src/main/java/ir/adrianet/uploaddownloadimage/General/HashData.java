package ir.adrianet.uploaddownloadimage.General;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashData {


    public static String SHA256(String data)
    {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            byte[] hash = hasher.digest(data.getBytes(Charset.forName("UTF-8")));
            return String.format("%064x", new BigInteger(1, hash));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
