package ru.vkhackathon.snack.domain;

import ru.vkhackathon.snack.MenuGroup;

/**
 * Наборы
 */
public class MenuGroupDAO extends CouchGeneric<MenuGroup> {

    private String foodId;

    public MenuGroupDAO() {
        super(TypeCouch.MENU_GROUP);
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Override
    public String getBeanId() {
        return getBean().getId();
    }

    @Override
    public void setBeanId(String id) {
        getBean().setId(id);
    }
}
