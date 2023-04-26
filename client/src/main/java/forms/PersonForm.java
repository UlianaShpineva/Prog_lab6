package forms;

import console.BlankConsole;
import console.Printable;
import utility.ScannerManager;
import models.Country;
import models.Location;
import models.Person;

import java.util.Scanner;

public class PersonForm extends Form<Person> {
    private final Printable console;
    private final Scanner scanner = ScannerManager.getScanner();

    public PersonForm(Printable console) {
        this.console = (ScannerManager.fileMode())
                ? new BlankConsole()
                : console;
    }

    /**
     * Запрашивает имя админа
     * @return Имя админа
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
     * Запрашивает номер паспорта
     * @return Номер паспорта
     */
    private String askPassportID() {
        String passportID;
        while (true){
            console.println("Введите номер паспорта:");
            passportID = scanner.nextLine().trim();
            return passportID;
        }
    }

    /**
     * Запрашивает страну
     * @return объект {@link Country}
     */
    private Country askNationality() {
        return new CountryForm(console).build();
    }

    /**
     * Запрашивает локацию
     * @return объект {@link Location}
     */
    private Location askLocation(){
        return new LocationForm(console).build();
    }

    /**
     * Создает объект {@link Person}
     * @return объект {@link Person}
     */
    @Override
    public Person build() {
        console.println("Создание админа группы:");
        Person person = new Person(askName(), askPassportID(), askNationality(), askLocation());
        console.println("Создание объекта админа окончено успешно");
        return person;
    }
}
