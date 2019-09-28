package ru.vkhackathon.snack.domain;

import ru.vkhackathon.snack.Brand;

/**
 * Домен для бренда
 */
public class BrandDAO extends CouchGeneric<Brand> {

    public BrandDAO() {
        super(TypeCouch.BRAND);
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
