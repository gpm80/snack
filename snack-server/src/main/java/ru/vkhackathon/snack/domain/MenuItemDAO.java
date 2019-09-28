package ru.vkhackathon.snack.domain;

import ru.vkhackathon.snack.MenuItem;

/**
 * Created by Petr Gusarov
 */
public class MenuItemDAO extends CouchGeneric<MenuItem> {

    private String foodCourtId;

    public MenuItemDAO() {
        super(TypeCouch.MENU_ITEM);
    }

    @Override
    public String getBeanId() {
        return getBean().getId();
    }

    @Override
    public void setBeanId(String id) {
        getBean().setId(id);
    }

    public String getFoodCourtId() {
        return foodCourtId;
    }

    public void setFoodCourtId(String foodCourtId) {
        this.foodCourtId = foodCourtId;
    }
}
