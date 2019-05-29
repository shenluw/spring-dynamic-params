package top.shenluw.sldp;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import top.shenluw.sldp.annotation.SlSecurity;

import java.util.Set;

/**
 * @author Shenluw
 * 创建日期：2019/2/22 10:40
 */
public abstract class AbstractSecureDynamicParamsMethodProcessor extends AbstractDynamicParamsMethodProcessor implements SecurityParameterAware {

    private boolean defaultSecure;
    private Encryptor encryptor;

    public Encryptor getEncryptor() {
        return encryptor;
    }

    @Override
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    public boolean isDefaultSecure() {
        return defaultSecure;
    }

    public void setDefaultSecure(boolean defaultSecure) {
        this.defaultSecure = defaultSecure;
    }

    protected final boolean isSecure(MethodParameter parameter, Class realClass) {
        if (encryptor == null) {
            return false;
        }
        SlSecurity slSecurity = getSlSecurity(parameter, realClass);
        if (slSecurity != null) {
            return slSecurity.enable();
        }
        return defaultSecure;
    }

    protected SlSecurity getSlSecurity(MethodParameter parameter, Class realClass) {
        SlSecurity slSecurity = parameter.getParameterAnnotation(SlSecurity.class);
        if (slSecurity == null) {
            Set<SlSecurity> securities = AnnotationUtils.getRepeatableAnnotations(realClass, SlSecurity.class);
            if (!securities.isEmpty()) {
                slSecurity = securities.iterator().next();
            }
        }
        return slSecurity;
    }

}
