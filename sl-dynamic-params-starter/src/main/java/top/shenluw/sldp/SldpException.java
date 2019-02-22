package top.shenluw.sldp;

/**
 * @author Shenluw
 * 创建日期：2019/2/22 11:38
 */
public class SldpException extends RuntimeException {
    public SldpException() {
    }

    public SldpException(String message) {
        super(message);
    }

    public SldpException(String message, Throwable cause) {
        super(message, cause);
    }

    public SldpException(Throwable cause) {
        super(cause);
    }

    public SldpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
