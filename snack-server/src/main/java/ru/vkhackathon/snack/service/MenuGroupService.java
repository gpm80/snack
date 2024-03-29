package ru.vkhackathon.snack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.MenuItem;
import ru.vkhackathon.snack.domain.MenuGroupDAO;
import ru.vkhackathon.snack.repository.MenuGroupRepository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petr Gusarov
 */
@Service
public class MenuGroupService {

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    public List<MenuGroup> findByFoodId(String foodId) {
        List<MenuGroupDAO> byFoodId = menuGroupRepository.findByFoodId(foodId);
        return byFoodId.stream()
                .map(MenuGroupDAO::syncGetBean)
                .collect(Collectors.toList());
    }

    public MenuGroup save(MenuGroup menuGroup, String foodId, File file) {
        // Обновим цену за набор
        int kkal = 0, price = 0;
        for (MenuItem menuItem : menuGroup.getMenuItems()) {
            price += menuItem.getPrice();
            kkal += menuItem.getKkal();
        }
        menuGroup.setPrice(price);
        menuGroup.setKkal(kkal);
        menuGroup.setImageUri(null);
        MenuGroupDAO save = menuGroupRepository.save(menuGroup, foodId, file);
        return save.syncGetBean();
    }
}
