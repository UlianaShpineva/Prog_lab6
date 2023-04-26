package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс локации
 */
public class Location implements Validatable, Serializable {
    private Long x; //Поле не может быть null
    private Integer y; //Поле не может быть null
    private Double z; //Поле не может быть null
    private String name; //Поле может быть null

    public Location(Long x, Integer y, Double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Проверяет корректность полей
     * @return Корректность полей
     */
    @Override
    public boolean validate() {
        return (!(this.x == null) && !(this.y == null) && !(this.z == null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x.equals(location.x) && y.equals(location.y) && z.equals(location.z) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }

    @Override
    public String toString() {
        return "models.Location{" + '\n' +
                "x = " + x + '\n' +
                "y = " + y + '\n' +
                "z = " + z + '\n' +
                "name = " + name + '\n' +
                '}';
    }
}
