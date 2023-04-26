package commands;

import exceptions.IllegalArgument;
import managers.CollectionManager;
import models.StudyGroup;
import network.Request;
import network.Response;
import network.ResponseStatus;

import java.util.Objects;

/**
 * Команда 'update'
 * Обновляет значение элемента коллекции, id которого равен заданному
 */
public class Update extends Command implements CollectionEditor {
    private CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update", " id {element} : обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить программу
     * @param request аргументы команды
     * @throws IllegalArgument неверные аргументы
     */
    @Override
    public Response execute(Request request) throws IllegalArgument {
        if (request.getArgs().isBlank()) throw new IllegalArgument();
        class NoSuchId extends RuntimeException{
        }
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            if (!collectionManager.checkExist(id)) throw new NoSuchId();
            if (Objects.isNull(request.getObject())){
                return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
            }
            StudyGroup newStudyGroup = request.getObject();
            collectionManager.editById(id, newStudyGroup);
            return new Response(ResponseStatus.OK, "Объект успешно обновлен");
        } catch (NoSuchId err) {
            return new Response(ResponseStatus.ERROR, "В коллекции нет элемента с таким id");
        } catch (NumberFormatException exception) {
            return new Response(ResponseStatus.ERROR,"id должно быть числом типа int");
        }
    }
}
