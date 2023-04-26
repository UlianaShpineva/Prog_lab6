package commands;

import console.Console;
import exceptions.IllegalArgument;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.ResponseStatus;

/**
 * Команда 'clear'
 * Очищает коллекцию
 */
public class Clear extends Command implements CollectionEditor {
    private CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", ": очистить коллекцию");
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
        collectionManager.clear();
        return new Response(ResponseStatus.OK, "Элементы удалены");
    }
}
