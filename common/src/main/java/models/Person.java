package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс админа группы
 */
public class Person implements Validatable, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String passportID; //Поле может быть null
    private Country nationality; //Поле не может быть null
    private Location location; //Поле может быть null

    public Person(String name, String passportID, Country nationality, Location location) {
        this.name = name;
        this.passportID = passportID;
        this.nationality = nationality;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Проверяет корректность полей
     * @return Корректность полей
     */
    @Override
    public boolean validate() {
        if (this.name == null || this.name.isEmpty()) return false;
        return !(this.nationality == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!name.equals(person.name)) return false;
        if (!Objects.equals(passportID, person.passportID)) return false;
        if (nationality != person.nationality) return false;
        return Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (passportID != null ? passportID.hashCode() : 0);
        result = 31 * result + nationality.hashCode();
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "models.Person{" + '\n' +
                "name = " + name + '\n' +
                "passportID = " + passportID + '\n' +
                "nationality = " + nationality + '\n' +
                "location = " + location + '\n' +
                '}';
    }
}
