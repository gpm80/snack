package ru.vkhackathon.snack.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.vkhackathon.snack.FoodCourt;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.common.GpsCalculator;
import ru.vkhackathon.snack.domain.CouchGeneric;
import ru.vkhackathon.snack.domain.FoodCourtDAO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Репозиторий для работы с фудкортами
 * Created by Petr Gusarov
 */
@Repository
@Views({
        @View(name = FoodCourtRepository.VIEW_NEAR_FOOD,
                map = "function(doc) { if (doc.type=='FOOD_COURT') { emit(doc.geoHash, doc) } }"),
        @View(name = FoodCourtRepository.VIEW_BY_TRC,
                map = "function(doc) { if (doc.type=='FOOD_COURT') { emit(doc.trcId, doc) } }")
})
public class FoodCourtRepository extends CouchDbRepositorySupport<FoodCourtDAO> {

    private static Logger logger = LoggerFactory.getLogger(FoodCourtRepository.class);
    public static final String VIEW_NEAR_FOOD = "viewNearFood";
    public static final String VIEW_BY_TRC = "viewByTrc";

    public FoodCourtRepository(CouchDbConnector db) {
        super(FoodCourtDAO.class, db);
        initStandardDesignDocument();
    }

    /**
     * Находит ближайшие к пользователю фудкорты
     *
     * @param gpsPoint
     */
    public List<FoodCourt> getNearFoodCourt(GpsPoint gpsPoint, int radius) {
        String[] rangeGeoHash = GpsCalculator.rangeGeoHash(gpsPoint, radius);
        ViewQuery query = createQuery(VIEW_NEAR_FOOD)
                .startKey(rangeGeoHash[1])
                .endKey(rangeGeoHash[2])
                .limit(1000);
        String s = query.buildQuery();
        logger.debug("query: {}", s);
        // Выберем из базы таковые
        List<FoodCourtDAO> result = db.queryView(query, FoodCourtDAO.class);
        List<FoodCourt> collect = result.stream()
                .map(CouchGeneric::syncGetBean)
                .collect(Collectors.toList());
        return collect;
    }

    public List<FoodCourtDAO> findByTrcId(String trcId) {
        return db.queryView(
                createQuery(VIEW_BY_TRC).key(trcId).limit(1000),
                FoodCourtDAO.class);
    }

    /**
     * Сохраняет бин в базу
     *
     * @param foodCourt
     * @return
     */
    public FoodCourt save(FoodCourt foodCourt) {
        FoodCourtDAO foodCourtDAO = new FoodCourtDAO();
        foodCourtDAO.setBean(foodCourt);
        foodCourtDAO.setTrcId(foodCourt.getTrc().getId());
        foodCourtDAO.setGeoHash(GpsCalculator.geoHash(foodCourt));
        if (foodCourt.getId() != null) {
            update(foodCourtDAO);
        } else {
            add(foodCourtDAO);
        }
        return foodCourtDAO.syncGetBean();
    }

    /**
     * Поиск фудкорта по id
     *
     * @param foodId id
     * @return
     */
    public FoodCourt findOne(String foodId) {
        FoodCourtDAO dao = get(foodId);
        return dao != null
                ? dao.syncGetBean()
                : null;
    }
}
