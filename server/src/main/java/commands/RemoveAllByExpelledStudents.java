package commands;

import exceptions.IllegalArgument;
import managers.CollectionManager;
import models.StudyGroup;
import network.Request;
import network.Response;
import network.ResponseStatus;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Команда 'remove_all_by_expelled_students'
 * Удаляет из коллекции все элементы, значение поля expelledStudents которого эквивалентно заданному
 */
public class RemoveAllByExpelledStudents extends Command implements CollectionEditor {
    private CollectionManager collectionManager;

    public RemoveAllByExpelledStudents(CollectionManager collectionManager) {
        super("remove_all_by_expelled_students", " expelledStudents : удалить из коллекции все элементы, значение поля expelledStudents которого эквивалентно заданному");
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
        try {
            int expelledStudents = Integer.parseInt(request.getArgs().trim());
            Collection<StudyGroup> toRemove = collectionManager.getCollection().stream()
                    .filter(Objects::nonNull)
                    .filter(studyGroup -> studyGroup.getExpelledStudents() == expelledStudents)
                    .collect(Collectors.toList());
            for (StudyGroup element:toRemove) {
                collectionManager.removeElement(element);
            }
            return new Response(ResponseStatus.OK, "Удалены элементы с таким expelled_students");
        } catch (NumberFormatException exception) {
            return new Response(ResponseStatus.ERROR, "expelled_students должно быть числом типа int");
        }
    }
}
