package commands;

import exceptions.IllegalArgument;
import managers.CollectionManager;
import models.StudyGroup;
import network.Request;
import network.Response;
import network.ResponseStatus;

import java.util.Objects;

/**
 * Команда 'add_if_max'
 * Добавляет элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
 */
public class AddIfMax extends Command implements CollectionEditor {
    private CollectionManager collectionManager;

    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max", " {element}: добавить элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить программу
     * @param request аргументы команды
     * @throws IllegalArgument неверные аргументы
     */
    @Override
    public Response execute(Request request) throws IllegalArgument {
        if (!request.getArgs().isBlank()) throw new IllegalArgument();
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        }
        StudyGroup newElement = request.getObject();
        if (newElement.compareTo(collectionManager.getCollection().stream()
                .filter(Objects::nonNull)
                .max(StudyGroup::compareTo)
                .orElse(null)) >= 1)
        {
            collectionManager.addElement(newElement);
            return new Response(ResponseStatus.OK,"Объект успешно добавлен");
        } else {
            return new Response(ResponseStatus.ERROR,"Элемент меньше максимального");
        }
    }
}
