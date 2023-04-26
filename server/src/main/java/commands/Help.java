package commands;

import exceptions.IllegalArgument;
import managers.CommandManager;
import network.Request;
import network.Response;
import network.ResponseStatus;

/**
 * Команда 'help'
 * Выводит справку по доступным командам
 */
public class Help extends Command{
    private CommandManager commandManager;
    public Help(CommandManager commandManager) {
        super("help", ": вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /**
     * Исполнить программу
     * @param request аргументы команды
     * @throws IllegalArgument неверные аргументы
     */
    @Override
    public Response execute(Request request) throws IllegalArgument {
        if (!request.getArgs().isBlank()) throw new IllegalArgument();
        return new Response(ResponseStatus.OK,
                String.join("\n",
                    commandManager.getCommands()
                            .stream().map(Command::toString).toList()));
    }
}
