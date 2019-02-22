package top.shenluw.sldp;

/**
 * @author Shenluw
 * 创建日期：2019/2/22 11:38
 */
public class SlSecurityException extends RuntimeException {
    public SlSecurityException() {
    }

    public SlSecurityException(String message) {
        super(message);
    }

    public SlSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlSecurityException(Throwable cause) {
        super(cause);
    }

    public SlSecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
