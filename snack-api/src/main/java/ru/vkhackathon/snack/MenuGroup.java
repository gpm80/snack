package ru.vkhackathon.snack;

/**
 * Группа меню
 */
public class MenuGroup {

    private static final long serialVersionUID = 1L;
    /**
     * ID объекта
     */
    private String id;
    /**
     * Наименование группы
     */
    private String title;
    /**
     * Описание группы
     */
    private String description;

    public MenuGroup() {
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
