package ru.vkhackathon.snack.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vkhackathon.snack.MenuItem;
import ru.vkhackathon.snack.service.MenuItemService;

/**
 * Created by Petr Gusarov
 */
@RestController
@RequestMapping(path = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemRest {

    @Autowired
    private MenuItemService menuItemService;

    @RequestMapping(path = "/get/by/food/{foodId}")
    public List<MenuItem> getMenuByFoodId(@PathVariable("foodId") String foodId) {
        return menuItemService.getByFoodCoourtId(foodId);
    }

    @RequestMapping(path = "/get/by/group/{groupId}")
    public List<MenuItem> getMenuByGroup(@PathVariable("groupId") String groupId){
        //TODO
        return new ArrayList<>();
    }


}
