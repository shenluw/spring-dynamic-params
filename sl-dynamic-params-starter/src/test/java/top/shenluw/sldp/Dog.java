package top.shenluw.sldp;

import org.hibernate.validator.constraints.Length;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 12:03
 */
public class Dog extends Animal {
    private String face;
    @Length(min = 6)
    private String speed;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "face='" + face + '\'' +
                ", speed='" + speed + '\'' +
                "} " + super.toString();
    }
}
