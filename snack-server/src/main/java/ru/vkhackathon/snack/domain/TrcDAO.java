package ru.vkhackathon.snack.domain;

import ru.vkhackathon.snack.Trc;

/**
 * Домен для ТРЦ
 */
public class TrcDAO extends CouchGeneric<Trc> {

    private String geoHash;

    public TrcDAO() {
        super(TypeCouch.TRC);
    }

    @Override
    public String getBeanId() {
        return getBean().getId();
    }

    @Override
    public void setBeanId(String id) {
        getBean().setId(id);
    }

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }
}
