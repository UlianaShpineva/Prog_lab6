package commands;


import exceptions.IllegalArgument;
import network.Request;
import network.Response;
import network.ResponseStatus;

/**
 * Команда 'exit'
 * Завершает программу (без сохранения в файл)
 */
public class Exit extends Command {
    public Exit() {
        super("exit", ": завершить программу (без сохранения в файл)");
    }

    /**
     * Исполнить программу
     * @param request аргументы команды
     * @throws IllegalArgument неверные аргументы
     */
    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.EXIT);
    }
}
