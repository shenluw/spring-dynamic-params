package top.shenluw.sldp;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 12:03
 */
public class Cat extends Animal {
    private String speed;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "speed='" + speed + '\'' +
                "} " + super.toString();
    }
}
