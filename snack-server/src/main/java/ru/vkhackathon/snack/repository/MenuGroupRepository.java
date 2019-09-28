package ru.vkhackathon.snack.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.stereotype.Repository;
import ru.vkhackathon.snack.domain.MenuGroupDAO;

import java.util.List;

/**
 * Created by Petr Gusarov
 */
@Repository
@Views({@View(name = "",
        map = "function(doc) { if (doc.type=='MENU_GROUP') { emit(doc.foodId, doc) } }")})
public class MenuGroupRepository extends CouchDbRepositorySupport<MenuGroupDAO> {

    public static final String VIEW_BY_FOOD = "viewByFood";

    public MenuGroupRepository(CouchDbConnector db) {
        super(MenuGroupDAO.class, db);
        initStandardDesignDocument();
    }

    public List<MenuGroupDAO> findByFoodId(String foodId) {
        return db.queryView(createQuery(VIEW_BY_FOOD).key(foodId), MenuGroupDAO.class);
    }
}
