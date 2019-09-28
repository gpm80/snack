package ru.vkhackathon.snack;

import java.util.HashSet;
import java.util.Set;

/**
 * Элемент меню
 */
public class MenuItem implements ImageUri {

    private static final long serialVersionUID = 1L;
    /**
     * ID объекта
     */
    private String id;
    /**
     * Название продукта
     */
    private String title;
    /**
     * Описание
     */
    private String description;

    /**
     * Типы спец меню
     */
    private Set<Special> specialSet;
    /**
     * Цена в копейках
     */
    private int price;
    /**
     * Акционная цена
     */
    private int salePrice;
    /**
     * Вес в граммах
     */
    private int weight;
    /**
     * Энергетическая ценность продукта на 100 г.
     */
    private int kkal;
    /**
     * Абсолютная ссылка на изображение продукта
     */
    private String imageUri;
    /**
     * Владелец меню
     */
    private FoodCourt parentFoodCourt;

    public MenuItem() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FoodCourt getParentFoodCourt() {
        return parentFoodCourt;
    }

    public void setParentFoodCourt(FoodCourt parentFoodCourt) {
        this.parentFoodCourt = parentFoodCourt;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getKkal() {
        return kkal;
    }

    public void setKkal(int kkal) {
        this.kkal = kkal;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
