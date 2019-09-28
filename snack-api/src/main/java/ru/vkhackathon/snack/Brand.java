package ru.vkhackathon.snack;

/**
 * Торговая марка
 */
public class Brand {

    private static final long serialVersionUID = 1L;
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

    /**
     * Относительная ссылка на логотип марки
     */
    private String logoUri;

    public Brand() {
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

    public String getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }
}
