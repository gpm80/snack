package ru.vkhackathon.snack.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.MenuItem;
import ru.vkhackathon.snack.domain.MenuItemDAO;
import ru.vkhackathon.snack.repository.MenuItemRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petr Gusarov
 */
@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * Возвращает список меню
     *
     * @param foodId id фудкорта
     * @return
     */
    public List<MenuItem> getByFoodCoourtId(String foodId) {
        if (StringUtils.isBlank(foodId)) {
            return new ArrayList<>();
        }
        return menuItemRepository.findByFoodId(foodId, 100);
    }

    public MenuItem save(MenuItem menuItem, String foodId, File file) {
        MenuItemDAO dao = menuItemRepository.save(menuItem, foodId, file);
        return dao.syncGetBean();
    }
}
