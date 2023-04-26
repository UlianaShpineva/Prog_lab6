package commands;

import console.Console;
import exceptions.IllegalArgument;
import managers.CollectionManager;
import models.StudyGroup;
import network.Request;
import network.Response;
import network.ResponseStatus;

import java.util.Objects;

/**
 * Команда 'average_of_expelled_students'
 * Выводит среднее значение поля expelledStudents для всех элементов коллекции
 */
public class AverageOfExpelledStudents extends Command {
    private CollectionManager collectionManager;

    public AverageOfExpelledStudents(CollectionManager collectionManager) {
        super("average_of_expelled_students", ": вывести среднее значение поля expelledStudents для всех элементов коллекции");
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
        Double toSort = collectionManager.getCollection().stream()
                .filter(Objects::nonNull)
                .mapToInt(StudyGroup::getExpelledStudents)
                .average().orElse(0);
        return new Response(ResponseStatus.OK, "Среднее значение поля expelledStudents: " + toSort.toString());

    }
}
