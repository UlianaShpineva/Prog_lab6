package forms;

import console.BlankConsole;
import console.Printable;
import utility.ScannerManager;
import models.Location;

import java.util.Scanner;

public class LocationForm extends Form<Location> {
    private final Printable console;
    private final Scanner scanner = ScannerManager.getScanner();

    public LocationForm(Printable console) {
        this.console = (ScannerManager.fileMode())
                ? new BlankConsole()
                : console;
    }

    /**
     * Запрашивает координату X
     * @return Координата X
     */
    private Long askX() {
        while (true) {
            console.println("Введите координату X:");
            String input = scanner.nextLine().trim();
            try {
                return Long.parseLong(input);
            } catch (NumberFormatException exception) {
                console.printError("X должно быть числом типа long");
            } catch (Throwable throwable) {
                console.printError("Непредвиденная ошибка!");
            }
        }
    }

    /**
     * Запрашивает координату Y
     * @return Координата Y
     */
    private int askY() {
        while (true) {
            console.println("Введите координату Y:");
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                console.printError("Y должно быть числом типа int");
            } catch (Throwable throwable) {
                console.printError("Непредвиденная ошибка!");
            }
        }
    }

    /**
     * Запрашивает координату Z
     * @return Координата Z
     */
    private double askZ() {
        while (true) {
            console.println("Введите координату Z:");
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException exception) {
                console.printError("Z должно быть числом типа double");
            } catch (Throwable throwable) {
                console.printError("Непредвиденная ошибка!");
            }
        }
    }

    /**
     * Запрашивает название локации
     * @return Имя локации
     */
    private String askName(){
        while (true){
            console.println("Введите название локации:");
            String name = scanner.nextLine().trim();
            return name;
        }
    }

    /**
     * Создает объект {@link Location}
     * @return объект {@link Location}
     */
    @Override
    public Location build() {
        return new Location(askX(), askY(), askZ(), askName());
    }
}
