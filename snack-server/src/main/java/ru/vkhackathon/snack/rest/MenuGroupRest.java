package ru.vkhackathon.snack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.service.MenuGroupService;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest для групп меню.
 */

@RestController()
@RequestMapping(path = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuGroupRest {

    @Autowired
    private MenuGroupService menuGroupService;

    @RequestMapping(path = "/get/by/food/{foodId}")
    public List<MenuGroup> getMenuGroupByFood(@PathVariable("foodId") String foodId) {
        return menuGroupService.findByFoodId(foodId);
    }
}
