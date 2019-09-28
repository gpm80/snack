package ru.vkhackathon.snack;

/**
 * Модель ТРЦ
 */
public class Trc extends GpsPoint {

    /**
     * ID объекта
     */
    private String id;
    /**
     * Нвзвание
     */
    private String title;
    /**
     * Описание
     */
    private String description;

    public Trc() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
