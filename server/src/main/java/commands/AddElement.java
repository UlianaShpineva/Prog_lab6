package commands;

import exceptions.IllegalArgument;
import managers.CollectionManager;
import models.StudyGroup;
import network.Request;
import network.Response;
import network.ResponseStatus;

import java.util.Objects;

/**
 * Команда 'add'
 * Добавляет новый элемент в коллекцию
 */
public class AddElement extends Command implements CollectionEditor {
    private CollectionManager collectionManager;

    public AddElement(CollectionManager collectionManager) {
        super("add", " {element} : добавить новый элемент в коллекцию");
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
        } else{
            request.getObject().setId(StudyGroup.incNextId());
            collectionManager.addElement(request.getObject());
            return new Response(ResponseStatus.OK, "Объект успешно добавлен");
        }



        // try {
            //console.println("Создание объекта StudyGroup:");
        //request.getObject().setId(CollectionManager.getNextId());
        //collectionManager.addElement(request.getObject());   //new StudyGroupForm(console).build());
            //console.println("Создание объекта StudyGroup окончено успешно");
          //  return new Response(ResponseStatus.OK, "Объект успешно добавлен");
        //} catch (InvalidForm invalidForm) {
          //  console.printError("Поля объекта не валидны. Объект не создан.");
        //}
    }
}
