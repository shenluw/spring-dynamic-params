package top.shenluw.sldp;

/**
 * @author Shenluw
 * 创建日期：2019/5/29 10:38
 */
public class SldpJsonException extends SldpException {
    public SldpJsonException() {
    }

    public SldpJsonException(String message) {
        super(message);
    }

    public SldpJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public SldpJsonException(Throwable cause) {
        super(cause);
    }

    public SldpJsonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
