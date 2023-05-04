package managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.Console;
import exceptions.InvalidForm;
import models.StudyGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
    private TreeSet<StudyGroup> collection = new TreeSet<StudyGroup>( new Comparator<StudyGroup>() {
        @Override
        public int compare(StudyGroup s1, StudyGroup s2) {
            return s1.compareTo(s2);
        }
    });
    private final FileManager fileManager;
    /**
     * Дата создания коллекции
     */
    private LocalDateTime lastInitTime;
    static final Logger collectionManagerLogger = LogManager.getLogger(CollectionManager.class);

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.lastInitTime = LocalDateTime.now();

        loadCollection();
    }

    /**
     * @return Коллекция
     */
    public TreeSet<StudyGroup> getCollection() {
        return collection;
    }

    /**
     * @return Время последней инициализации
     */
    public String getLastInitTime() {
        return lastInitTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * @return Тип коллекции
     */
    public String collectionType() {
        return collection.getClass().getName();
    }

    /**
     * @return Размер коллекции
     */
    public int collectionSize() {
        return collection.size();
    }

    /**
     * Очищает коллекцию, обновляет время инициализации
     */
    public void clear(){
        this.collection.clear();
        lastInitTime = LocalDateTime.now();
        collectionManagerLogger.info("Коллекция очищена");
    }

    /**
     * Добавляет элемент в коллекцию
     * @param studyGroup Элемент
     */
    public void addElement(StudyGroup studyGroup) {
        collection.add(studyGroup);
        collectionManagerLogger.info("Добавлен объект в коллекцию", studyGroup);
    }

    public void addElements(Collection<StudyGroup> collection) throws InvalidForm{
        if (collection == null) return;
        for (StudyGroup studyGroup:collection){
            this.addElement(studyGroup);
        }
    }

    /**
     * @param id id
     * @return Существование элемента с заданным id
     */
    public boolean checkExist(int id) {
        return collection.stream()
                .anyMatch((x) -> x.getId() == id);
    }

    /**
     * @param id id
     * @return Элемент с таким id или null
     */
    public StudyGroup getById(int id) {
        for (StudyGroup element : collection) {
            if (element.getId() == id) return element;
        }
        return null;
    }

    /**
     * Удаляет элемент из коллекции
     * @param studyGroup Элемент
     */
    public void removeElement(StudyGroup studyGroup){
        collection.remove(studyGroup);
    }

    /**
     * Изменяиет элемент коллекции по id
     * @param id id
     * @param newElement Новый элемент
     * @throws InvalidForm Элемент отсутствует
     */
    public void editById(int id, StudyGroup newElement) {//throws InvalidForm{
        StudyGroup pastElement = this.getById(id);
        this.removeElement(pastElement);
        newElement.setId(id);
        this.addElement(newElement);
        StudyGroup.updateId(this.getCollection());
        collectionManagerLogger.info("Объект с айди " + id + " изменен", newElement);
    }

    /**
     * Загружает коллекцию из файла
     */
    private void loadCollection() {
        collection = (TreeSet<StudyGroup>) fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }
}
