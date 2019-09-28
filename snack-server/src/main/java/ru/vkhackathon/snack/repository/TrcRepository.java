package ru.vkhackathon.snack.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.stereotype.Repository;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.Trc;
import ru.vkhackathon.snack.common.GpsCalculator;
import ru.vkhackathon.snack.domain.TrcDAO;

import java.util.List;

/**
 * Created by Petr Gusarov
 */
@Repository
@Views({
        @View(name = TrcRepository.VIEW_TRC_GEOHASH,
                map = "function(doc) { if (doc.type=='TRC') { emit(doc.geoHash, doc) } }")
})
public class TrcRepository extends CouchDbRepositorySupport<TrcDAO> {

    static final String VIEW_TRC_GEOHASH = "viewTrcByGeoHash";

    public TrcRepository(CouchDbConnector db) {
        super(TrcDAO.class, db);
        initStandardDesignDocument();
    }

    /**
     * Поиск ТРЦ в указанном радиусе
     *
     * @param point
     * @param radius
     * @return
     */
    public List<TrcDAO> findByNearPoint(GpsPoint point, int radius) {
        String[] geos = GpsCalculator.rangeGeoHash(point, radius);
        ViewQuery query = createQuery(VIEW_TRC_GEOHASH)
                .startKey(geos[1])
                .endKey(geos[2])
                .limit(1000);
        return db.queryView(query, TrcDAO.class);
    }

    /**
     * Сохраняем
     *
     * @param trc
     * @return
     */
    public TrcDAO save(Trc trc) {
        TrcDAO trcDAO = new TrcDAO();
        trcDAO.setBean(trc);
        trcDAO.setGeoHash(GpsCalculator.geoHash(trc));
        add(trcDAO);
        return trcDAO;
    }
}
