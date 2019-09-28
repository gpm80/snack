package ru.vkhackathon.snack.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.domain.MenuGroupDAO;

import java.io.File;
import java.util.List;

/**
 * Created by Petr Gusarov
 */
@Repository
@Views({@View(name = MenuGroupRepository.VIEW_BY_FOOD,
        map = "function(doc) { if (doc.type=='MENU_GROUP') { emit(doc.foodId, doc) } }")})
public class MenuGroupRepository extends CouchDbRepositorySupport<MenuGroupDAO> {

    public static final String VIEW_BY_FOOD = "viewByFood";
    @Autowired
    private AttachmentFileCouchDb attachmentFileCouchDb;


    public MenuGroupRepository(CouchDbConnector db) {
        super(MenuGroupDAO.class, db);
        initStandardDesignDocument();
    }

    public List<MenuGroupDAO> findByFoodId(String foodId) {
        return db.queryView(createQuery(VIEW_BY_FOOD).key(foodId), MenuGroupDAO.class);
    }

    /**
     * Сохраняет
     *
     * @param menuGroup группа меню (набор)
     */
    public MenuGroupDAO save(MenuGroup menuGroup, String foodId, File file) {
        MenuGroupDAO mg = new MenuGroupDAO();
        mg.setBean(menuGroup);
        mg.setFoodId(foodId);
        if (mg.isNew()) {
            add(mg);
        } else {
            update(mg);
        }
        attachmentFileCouchDb.saveAttach(file, null, menuGroup, mg);
        return mg;
    }
}
