package commands;

import exceptions.IllegalArgument;
import managers.CollectionManager;
import models.StudyGroup;
import network.Request;
import network.Response;
import network.ResponseStatus;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Команда 'print_ascending'
 * Выводит элементы коллекции в порядке возрастания
 */
public class PrintAscending extends Command {
    private CollectionManager collectionManager;

    public PrintAscending(CollectionManager collectionManager) {
        super("print_ascending", ": вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
    }

    /**
     * Исполнить программу
     * @param request аргументы команды
     * @throws IllegalArgument неверные аргументы
     */
    @Override
    public Response execute(Request request) {
        Collection<StudyGroup> collection = collectionManager.getCollection().stream()
                .sorted(StudyGroup::compareTo)
                //.forEach(p -> p.toString())
                .collect(Collectors.toList());
        return new Response(ResponseStatus.OK, "Коллекция в порядке возрастания: ", collection);
    }
}
