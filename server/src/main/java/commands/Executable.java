package commands;

import exceptions.IllegalArgument;
import network.Request;
import network.Response;

/**
 * Интерфейс для выполняемых команд
 */
public interface Executable {
    Response execute(Request request) throws IllegalArgument;
}
