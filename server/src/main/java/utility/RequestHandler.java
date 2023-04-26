package utility;

import exceptions.CommandRuntimeError;
import exceptions.IllegalArgument;
import exceptions.NoSuchCommand;
import managers.CommandManager;
import network.Request;
import network.Response;
import network.ResponseStatus;

public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response handle(Request request) {
        try {
            return commandManager.execute(request);
        } catch (IllegalArgument e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS, "Неверное использование аргументов команды");
        } catch (CommandRuntimeError e) {
            return new Response(ResponseStatus.ERROR, "Ошибка при исполнении программы");
        } catch (NoSuchCommand e) {
            return new Response(ResponseStatus.ERROR, "Такой команды нет в списке");
        }
    }
}
