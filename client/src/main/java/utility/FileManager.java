package utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import console.Console;
import exceptions.IllegalArgument;
import exceptions.NoSuchCommand;
import exceptions.ScriptRecursionException;
import models.StudyGroup;

import java.io.*;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Класс для работы с файлами
 */
public class FileManager {
    private final String fileName;
    private final Console console;
    private final RuntimeManager runtimeManager;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .create();

    private static List<File> scriptStack = new LinkedList<>();

    public FileManager(Console console, String fileName, RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Сохраняет коллекцию в файл
     * @param collection Коллекция
     */
    public void writeCollection(Collection<StudyGroup> collection) {
        try(PrintWriter printWriter = new PrintWriter(new File(fileName)))
        {
            printWriter.println(gson.toJson(collection));
            console.println("Коллекция сохранена в файл " + fileName);
        }
        catch(IOException e){
            console.printError("Коллекция не сохранена");
        }
    }

    /**
     * Читает коллекцию из файла
     * @return Коллекция
     */
    public Collection<StudyGroup> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new InputStreamReader(new FileInputStream(fileName))) {
                Type collectionType = new TypeToken<TreeSet<StudyGroup>>(){}.getType();
                var reader = new BufferedReader(fileReader);
                StringBuilder jsonString = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.length() == 0) {
                    jsonString = new StringBuilder("[]");
                }

                TreeSet<StudyGroup> tmpCollection = gson.fromJson(jsonString.toString(), collectionType);
                TreeSet<StudyGroup> collection = new TreeSet<StudyGroup>();
                List<Integer> tmpIdOfEach = new ArrayList<>();
                List<Integer> idOfEach = new ArrayList<>();
                tmpCollection.forEach(p -> {
                    if (!p.validate()) {
                        console.printError("Элемент с id = " + p.getId() + " имеет невалидные поля");
                    } else {
                        if (tmpIdOfEach.isEmpty()) {
                            idOfEach.add(p.getId());
                            collection.add(p);
                        } else {
                            for (int el : tmpIdOfEach) {
                                if (p.getId() == el) {
                                    console.printError("Элемент с id = " + p.getId() + " уже находится в коллекции");
                                } else {
                                    idOfEach.add(p.getId());
                                    collection.add(p);
                                }
                            }
                        }
                    }
                    for (int el : idOfEach) {
                        boolean test = false;
                        for (int tmpEl : tmpIdOfEach) {
                            if (el == tmpEl) {
                                test = true;
                            }
                        }
                        if (!test) {
                            tmpIdOfEach.add(el);
                        }
                    }
                });
                console.println("Коллекция загружена из файла");
                StudyGroup.updateId(collection);
                return collection;
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст");
            }catch (IOException ex) {
                console.printError("Непредвиденная ошибка!");
            }
        } else {
            console.printError("Файл не обнаружен");
        }
        return new TreeSet<>();
    }

    /**
     * Режим исполнения скрипта
     * @param fileName Имя скрипта
     */
   /* public void scriptMode(String fileName) {
        /*if (!new File(fileName).exists()) {
            fileName = fileName;
        }
        scriptStack.add(new File(fileName));
        try (Scanner scriptScanner = new Scanner(new File(fileName))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = ScannerManager.getScanner();
            ScannerManager.setScanner(scriptScanner);
            ScannerManager.setFileMode();
            do {
                String userCommand = scriptScanner.nextLine().trim() + " ";
                while (scriptScanner.hasNextLine() && userCommand.split(" ", 2)[0].isEmpty()) {
                    userCommand = scriptScanner.nextLine().trim() + " ";
                }
                if (userCommand.split(" ", 2)[0].equals("execute_script")) {
                    for (File script : scriptStack) {
                        if (new File(userCommand.split(" ", 2)[1].trim()).equals(script)) {
                            throw new ScriptRecursionException();
                        }
                    }
                }
                console.println("$ " + String.join(" ", userCommand));
                runtimeManager.launch(userCommand.split(" ", 2));
            } while (scriptScanner.hasNextLine());

            ScannerManager.setScanner(tmpScanner);
            ScannerManager.setUserMode();

        } catch (FileNotFoundException e) {
            console.printError("Файл не обнаружен");
        } catch (NoSuchElementException e) {
            console.printError("Файл пустой");
        } catch (ScriptRecursionException e) {
            console.printError("Скрипт не может вызываться рекурсивно");
        } catch (NoSuchCommand e) {
            console.printError("Такой команды нет в списке");
        } catch (IllegalArgument e) {
            console.printError("Введены неправильные аргументы команды");
        }

    }*/
}
