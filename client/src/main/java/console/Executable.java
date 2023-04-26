package console;

import exceptions.IllegalArgument;

/**
 * Интерфейс для выполняемых команд
 */
public interface Executable {
    void execute(String args) throws IllegalArgument;
}
