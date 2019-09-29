package ru.vkhackathon.snack;

import java.util.ArrayList;
import java.util.List;

/**
 * Группа меню
 */
public class MenuGroup implements ImageUri {

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

    /**
     * Изображение
     */
    private String imageUri;

    /**
     * Цена в рублях
     */
    private int price;
    /**
     * Родительский фуд
     */
    private FoodCourt foodCourt;

    /**
     * Набор подменю
     */
    private List<MenuItem> menuItems;

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

    public FoodCourt getFoodCourt() {
        return foodCourt;
    }

    public void setFoodCourt(FoodCourt foodCourt) {
        this.foodCourt = foodCourt;
    }

    public List<MenuItem> getMenuItems() {
        if (menuItems == null) {
            menuItems = new ArrayList<MenuItem>();
        }
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
