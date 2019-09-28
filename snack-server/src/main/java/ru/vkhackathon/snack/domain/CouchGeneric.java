package ru.vkhackathon.snack.domain;

import org.ektorp.support.CouchDbDocument;

/**
 * Created by Petr Gusarov
 */
public abstract class CouchGeneric<T> extends CouchDbDocument {

    private final TypeCouch type;
    private T bean;

    protected CouchGeneric(TypeCouch type) {
        this.type = type;
    }

    @Override
    public String getId() {
        if (super.getId() == null && bean != null) {
            return getBeanId();
        }
        return super.getId();
    }

    /**
     * Проверка на новую сущность
     *
     * @return
     */
    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public void setId(String id) {
        super.setId(id);
        if (bean != null) {
            setBeanId(id);
        }
    }

    /**
     * Синхронизировать с внутренним бином
     */
    public T syncGetBean() {
        setId(getId());
        return getBean();
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public abstract String getBeanId();

    public abstract void setBeanId(String id);

    public TypeCouch getType() {
        return type;
    }
}
