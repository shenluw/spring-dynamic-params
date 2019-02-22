package top.shenluw.sldp.encrypt;

import org.springframework.util.Base64Utils;
import top.shenluw.sldp.Encryptor;
import top.shenluw.sldp.SlSecurityException;

import java.nio.charset.Charset;

/**
 * base64 只是编码，不是加密，此处只做示例
 *
 * @author Shenluw
 * 创建日期：2019/2/22 11:32
 */
public class B64Encryptor implements Encryptor {
    private Charset charset;

    public B64Encryptor(Charset charset) {
        this.charset = charset;
    }

    @Override
    public byte[] encrypt(String text) throws SlSecurityException {
        try {
            return Base64Utils.encode(text.getBytes(charset));
        } catch (Exception e) {
            throw new SlSecurityException("sldp base64 encrypt data not valid", e);
        }
    }

    @Override
    public byte[] decrypt(String encryptedText) throws SlSecurityException {
        try {
            return Base64Utils.decode(encryptedText.getBytes(charset));
        } catch (Exception e) {
            throw new SlSecurityException("sldp base64 decrypt data not valid", e);
        }
    }

    @Override
    public Charset getCharset() {
        return charset;
    }
}
