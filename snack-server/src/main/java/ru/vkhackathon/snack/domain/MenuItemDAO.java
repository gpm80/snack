package ru.vkhackathon.snack.domain;

import ru.vkhackathon.snack.MenuItem;

/**
 * Created by Petr Gusarov
 */
public class MenuItemDAO extends CouchGeneric<MenuItem> {

    /**
     * Связка с кафе
     */
    private String foodId;

    public MenuItemDAO() {
        super(TypeCouch.MENU_ITEM);
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
