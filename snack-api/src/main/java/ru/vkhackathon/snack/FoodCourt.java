package ru.vkhackathon.snack;

import java.util.HashSet;
import java.util.Set;

/**
 * Фудкорт
 */
public class FoodCourt extends GpsPoint {

    private static final long serialVersionUID = 1L;
    /**
     * ID объекта
     */
    private String id;
    /**
     * Название пункта питания
     */
    private String title;
    /**
     * Описание
     */
    private String description;
    /**
     * Бренд
     */
    private Brand brand;

    /**
     * Привязка к трц
     */
    private Trc trc;
    /**
     * Поддерживаенмые спец меню
     */
    private Set<Special> specialSet;

    public FoodCourt() {
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Trc getTrc() {
        return trc;
    }

    public void setTrc(Trc trc) {
        this.trc = trc;
    }

    public Set<Special> getSpecialSet() {
        if (specialSet == null) {
            specialSet = new HashSet<Special>();
        }
        return specialSet;
    }

    public void setSpecialSet(Set<Special> specialSet) {
        this.specialSet = specialSet;
    }
}
