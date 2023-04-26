package forms;

/**
 * Абстрактный класс для пользовательских форм ввода
 * @param <T> класс формы
 */
public abstract class Form<T> {
    public abstract T build();
}
