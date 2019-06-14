package top.shenluw.sldp;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import top.shenluw.sldp.jsontype.jackson.TypeAliasResolverBuilder;

import java.util.List;

/**
 * @author Shenluw
 * 创建日期：2019/6/14 16:40
 */
public class Mix extends Animal {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    @JsonTypeResolver(TypeAliasResolverBuilder.class)
    private Animal dog;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
    @JsonTypeResolver(TypeAliasResolverBuilder.class)
    private Animal cat;

    @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
    @JsonTypeResolver(TypeAliasResolverBuilder.class)
    private List<Animal> animals;

    public Animal getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Animal getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public String toString() {
        return "Mix{" +
                "dog=" + dog +
                ", cat=" + cat +
                ", animals=" + animals +
                '}';
    }
}
