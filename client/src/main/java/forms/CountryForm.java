package forms;

import console.BlankConsole;
import console.Printable;
import utility.ScannerManager;
import models.Country;

import java.util.Scanner;

/**
 * Форма для страны
 */
public class CountryForm extends Form<Country> {
    private final Printable console;
    private final Scanner scanner = ScannerManager.getScanner();

    public CountryForm(Printable console) {
        this.console = (ScannerManager.fileMode())
                ? new BlankConsole()
                : console;
    }

    /**
     * Создает объект {@link Country}
     * @return объект {@link Country}
     */
    @Override
    public Country build() {
        console.println("Доступные страны:");
        console.println(Country.names());
        while (true) {
            console.println("Введите страну:");
            String input = scanner.nextLine().trim();
            try{
                return Country.valueOf(input);
            } catch (IllegalArgumentException exception){
                console.printError("Такой страны нет в списке");
                if (ScannerManager.fileMode()) return null;
            } catch (Throwable throwable) {
                console.printError("Непредвиденная ошибка!");
            }
        }
    }
}
