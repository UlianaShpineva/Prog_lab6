package models;

import java.io.Serializable;

/**
 * Класс стран
 */
public enum Country implements Serializable {
    UNITED_KINGDOM,
    USA,
    GERMANY;

    /**
     * @return Названия стран
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
