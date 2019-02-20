package top.shenluw.sldp;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 12:03
 */
public class BDog extends Dog {
    private String height;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "BDog{" +
                "height='" + height + '\'' +
                "} " + super.toString();
    }
}
