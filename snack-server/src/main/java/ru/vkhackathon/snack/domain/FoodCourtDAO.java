package ru.vkhackathon.snack.domain;

import ru.vkhackathon.snack.FoodCourt;

/**
 * Created by Petr Gusarov
 */
public class FoodCourtDAO extends CouchGeneric<FoodCourt> {

    private String brandId;
    private String trcId;
    private String geoHash;

    public FoodCourtDAO() {
        super(TypeCouch.FOOD_COURT);
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

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getTrcId() {
        return trcId;
    }

    public void setTrcId(String trcId) {
        this.trcId = trcId;
    }
}
