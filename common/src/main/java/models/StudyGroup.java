package models;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Класс группы
 */
public class StudyGroup implements Validatable, Comparable<StudyGroup>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private static int nextId;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long studentsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private int expelledStudents; //Значение поля должно быть больше 0
    private int transferredStudents; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

    public StudyGroup(String name, Coordinates coordinates, ZonedDateTime creationDate, Long studentsCount, int expelledStudents, int transferredStudents, FormOfEducation formOfEducation, Person groupAdmin) {
        this.id = incNextId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.transferredStudents = transferredStudents;
        this.formOfEducation = formOfEducation;
        this.groupAdmin = groupAdmin;
    }

    /**
     * Обновляет id для следующего элемента
     * @param collection Коллекция
     */
    public static void updateId(TreeSet<StudyGroup> collection){
        int maxId = 0;
        for (StudyGroup studyGroup: collection){
            if (Objects.isNull(studyGroup)) continue;
            maxId = Math.max(maxId, studyGroup.getId());
        }
        nextId = maxId + 1;
    }

    /**
     * Увеличивает id
     * @return id
     */
    public static int incNextId(){
        return nextId++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public int getExpelledStudents() {
        return expelledStudents;
    }

    public void setExpelledStudents(int expelledStudents) {
        this.expelledStudents = expelledStudents;
    }

    public int getTransferredStudents() {
        return transferredStudents;
    }

    public void setTransferredStudents(int transferredStudents) {
        this.transferredStudents = transferredStudents;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    /**
     * Сравнивает элементы коллекции
     * @param o the object to be compared.
     * @return 0 if equal, negative if less than object, positive if greater than object
     */
    @Override
    public int compareTo(StudyGroup o) {
        if (Objects.isNull(o)) return 1;
        return Float.compare(this.getName().length(), o.getName().length());
    }

    /**
     * Проверяет корректность полей
     * @return Корректность полей
     */
    @Override
    public boolean validate() {
        if (this.id < 0) return false;
        if (this.name == null || this.name.isEmpty()) return false;
        if (this.coordinates == null) return false;
        if (this.creationDate == null) return false;
        if (this.studentsCount == null || this.studentsCount <= 0) return false;
        if (this.expelledStudents <= 0) return false;
        if (this.transferredStudents <= 0) return false;
        return (this.formOfEducation != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudyGroup that = (StudyGroup) o;

        if (id != that.id) return false;
        if (expelledStudents != that.expelledStudents) return false;
        if (transferredStudents != that.transferredStudents) return false;
        if (!name.equals(that.name)) return false;
        if (!coordinates.equals(that.coordinates)) return false;
        if (!creationDate.equals(that.creationDate)) return false;
        if (!studentsCount.equals(that.studentsCount)) return false;
        if (formOfEducation != that.formOfEducation) return false;
        return Objects.equals(groupAdmin, that.groupAdmin);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + coordinates.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + studentsCount.hashCode();
        result = 31 * result + expelledStudents;
        result = 31 * result + transferredStudents;
        result = 31 * result + formOfEducation.hashCode();
        result = 31 * result + (groupAdmin != null ? groupAdmin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "models.StudyGroup{" + '\n' +
                "id = " + id + '\n' +
                "name = " + name + '\n' +
                "coordinates = " + coordinates + '\n' +
                "creationDate = " + creationDate + '\n' +
                "studentsCount = " + studentsCount + '\n' + '\n' +
                "expelledStudents = " + expelledStudents + '\n' +
                "transferredStudents = " + transferredStudents + '\n' +
                "formOfEducation = " + formOfEducation + '\n' +
                "groupAdmin = " + groupAdmin + '\n' +
                '}';
    }
}
