package top.shenluw.sldp;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 12:03
 */
public class Fly implements DynamicModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fly{" +
                "name='" + name + '\'' +
                '}';
    }
}
