package ru.vkhackathon.snack.domain;

import ru.vkhackathon.snack.MenuItem;

/**
 * Created by Petr Gusarov
 */
public class MenuItemDAO extends CouchGeneric<MenuItem> {

    /**
     * Связка с кафе
     */
    private String foodCourtId;
    /**
     * Связка с набором
     */
    private String menuGroupId;

    public MenuItemDAO() {
        super(TypeCouch.MENU_ITEM);
    }

    public String getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(String menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public String getFoodCourtId() {
        return foodCourtId;
    }

    public void setFoodCourtId(String foodCourtId) {
        this.foodCourtId = foodCourtId;
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
