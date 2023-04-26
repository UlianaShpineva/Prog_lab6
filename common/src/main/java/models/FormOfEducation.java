package models;

import java.io.Serializable;

/**
 * Класс форм обучения
 */
public enum FormOfEducation implements Serializable {
    DISTANCE_EDUCATION,
    FULL_TIME_EDUCATION,
    EVENING_CLASSES;

    /**
     * @return Названия форм обучения
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var forms : values()) {
            nameList.append(forms.name());
            nameList.append("\n");
        }
        return nameList.substring(0, nameList.length() - 1);
    }
}
