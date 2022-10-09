package np.com.kshitij.commons;

import org.springframework.util.Base64Utils;

public class Base64Encoder {
    private Base64Encoder() {
        //no instance
    }

    public static String encode(String str) {
        return Base64Utils.encodeToString(str.getBytes());
    }

    public static String decode(String str) {
        return new String(Base64Utils.decodeFromString(str));
    }
}
