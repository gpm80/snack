package ru.vkhackathon.snack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.domain.MenuGroupDAO;
import ru.vkhackathon.snack.repository.MenuGroupRepository;

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
}
