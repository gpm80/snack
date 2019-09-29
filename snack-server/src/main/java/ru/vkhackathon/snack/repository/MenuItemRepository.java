package ru.vkhackathon.snack.repository;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vkhackathon.snack.MenuItem;
import ru.vkhackathon.snack.domain.CouchGeneric;
import ru.vkhackathon.snack.domain.MenuItemDAO;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Petr Gusarov
 */
@Repository
@Views({@View(name = MenuItemRepository.VIEW_BY_FOOD_ID,
        map = "function(doc) { if (doc.type=='MENU_ITEM') { emit(doc.foodId, doc) } }")})
public class MenuItemRepository extends CouchDbRepositorySupport<MenuItemDAO> {

    @Autowired
    public FoodCourtRepository foodCourtRepository;
    @Autowired
    private AttachmentFileCouchDb attachmentFileCouchDb;
    public static final String VIEW_BY_FOOD_ID = "viewByFoodId";

    public MenuItemRepository(CouchDbConnector db) {
        super(MenuItemDAO.class, db);
        initStandardDesignDocument();
    }

    /**
     * Поиск списка меню по фудкорту
     *
     * @param foodId id фудкорта
     * @return
     */
    public List<MenuItem> findByFoodId(String foodId, int limit) {
        ViewQuery query = createQuery(VIEW_BY_FOOD_ID)
                .key(foodId)
                .limit(limit);
        List<MenuItemDAO> menuItemDAOS = db.queryView(query, type);
        return menuItemDAOS.stream()
                .peek(dao -> Optional
                        .ofNullable(foodCourtRepository.get(dao.getFoodId()))
                        .ifPresent(food -> dao.getBean().setParentFoodCourt(food.syncGetBean()))
                ).peek(dao -> attachmentFileCouchDb.lookImageUri(dao))
                .map(CouchGeneric::syncGetBean)
                .collect(Collectors.toList());
    }

    /**
     * Сохраняет элемент меню
     *
     * @param menuItem элемент меню
     * @return
     */
    public MenuItemDAO save(MenuItem menuItem, String foodId, File file) {
        MenuItemDAO menuItemDAO = new MenuItemDAO();
        // Занулим чтобы не хранить БД
        menuItem.setParentFoodCourt(null);
        menuItemDAO.setBean(menuItem);
        menuItemDAO.setFoodId(foodId);
        if (menuItemDAO.getId() == null) {
            add(menuItemDAO);
        } else {
            update(menuItemDAO);
        }
        attachmentFileCouchDb.saveAttach(file, null, menuItem, menuItemDAO);
        return menuItemDAO;
    }
}
