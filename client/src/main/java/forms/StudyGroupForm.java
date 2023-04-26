package forms;

import console.BlankConsole;
import console.Printable;
import utility.ScannerManager;
import models.Coordinates;
import models.FormOfEducation;
import models.Person;
import models.StudyGroup;

import java.time.ZonedDateTime;
import java.util.Scanner;

public class StudyGroupForm extends Form<StudyGroup> {
    private final Printable console;
    private final Scanner scanner = ScannerManager.getScanner();

    public StudyGroupForm(Printable console) {
        this.console = (ScannerManager.fileMode())
                ? new BlankConsole()
                : console;
    }

    /**
     * Запрашивает имя группы
     * @return Имя группы
     */
    private String askName() {
        String name;
        while (true){
            console.println("Введите имя:");
            name = scanner.nextLine().trim();
            if (name.isBlank()){
                console.printError("Имя не может быть пустым");
                if (ScannerManager.fileMode()) return "";
            }
            else{
                return name;
            }
        }
    }

    /**
     * Запрашивает координаты
     * @return объект {@link Coordinates}
     */
    private Coordinates askCoordinates() {
        return new CoordinatesForm(console).build();
    }

    /**
     * Запрашивает количество студентов
     * @return Количество студентов
     */
    private Long askStudentsCount() {
        while (true) {
            console.println("Введите количество студентов:");
            String input = scanner.nextLine().trim();
            try {
                if (input == null || Long.parseLong(input) <= 0) {
                    console.printError("Число студентов должно быть положительным");
                    if (ScannerManager.fileMode()) return (long) -1;
                } else {
                    return Long.parseLong(input);
                }
            } catch (NumberFormatException exception) {
                console.printError("Число студентов должно быть числом типа long");
            } catch (Throwable throwable) {
                console.printError("Непредвиденная ошибка!");
            }
        }
    }

    /**
     * Запрашивает количество отчисленных студентов
     * @return Количество отчисленных студентов
     */
    private int askExpelledStudents() {
        while (true) {
            console.println("Введите количество отчисленных студентов:");
            String input = scanner.nextLine().trim();
            try {
                if (Integer.parseInt(input) <= 0) {
                    console.printError("Количество отчисленных студентов должно быть положительным");
                    if (ScannerManager.fileMode()) return -1;
                } else {
                    return Integer.parseInt(input);
                }
            } catch (NumberFormatException exception) {
                console.printError("Число студентов должно быть числом типа int");
            } catch (Throwable throwable) {
                console.printError("Непредвиденная ошибка!");
            }
        }
    }

    /**
     * Запрашивает количество переведенных студентов
     * @return Количество переведенных студентов
     */
    private int askTransferredStudents() {
        while (true) {
            console.println("Введите количество переведенных студентов:");
            String input = scanner.nextLine().trim();
            try {
                if (Integer.parseInt(input) <= 0) {
                    console.printError("Количество переведенных студентов должно быть положительным");
                    if (ScannerManager.fileMode()) return -1;
                } else {
                    return Integer.parseInt(input);
                }
            } catch (NumberFormatException exception) {
                console.printError("Число студентов должно быть числом типа int");
            } catch (Throwable throwable) {
            console.printError("Непредвиденная ошибка!");
            }
        }
    }

    /**
     * Запрашивает форму обучения
     * @return объект {@link FormOfEducation}
     */
    private FormOfEducation askFormOfEducation(){
        return new FormOfEducationForm(console).build();
    }

    /**
     * Запрашивает админа группы
     * @return объект {@link Person}
     */
    private Person askGroupAdmin(){
        return new PersonForm(console).build();
    }

    /**
     * Создает объект {@link StudyGroup}
     * @return объект {@link StudyGroup}
     */
    @Override
    public StudyGroup build() {
        return new StudyGroup(askName(),
                askCoordinates(),
                ZonedDateTime.now(),
                askStudentsCount(),
                askExpelledStudents(),
                askTransferredStudents(),
                askFormOfEducation(),
                askGroupAdmin());
    }
}
