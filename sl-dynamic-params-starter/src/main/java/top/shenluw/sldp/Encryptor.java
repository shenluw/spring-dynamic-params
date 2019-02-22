package top.shenluw.sldp;

import org.springframework.lang.NonNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Shenluw
 * 创建日期：2019/2/21 17:45
 */
public interface Encryptor {

    byte[] encrypt(@NonNull String text) throws SlSecurityException;

    byte[] decrypt(@NonNull String encryptedText) throws SlSecurityException;

    Charset getCharset();

    Encryptor NOOP = new Encryptor() {
        @Override
        public byte[] encrypt(String text) {
            return text.getBytes(getCharset());
        }

        @Override
        public byte[] decrypt(String encryptedText) {
            return encryptedText.getBytes(getCharset());
        }

        @Override
        public Charset getCharset() {
            return StandardCharsets.UTF_8;
        }
    };

}
