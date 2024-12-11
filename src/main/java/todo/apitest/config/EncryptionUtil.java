package todo.apitest.config;


import java.util.Base64;

public class EncryptionUtil {

    public static String decodeToBase64(String data) {
        return new String(Base64.getDecoder().decode(data));
    }
}
