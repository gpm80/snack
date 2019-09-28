package ru.vkhackathon.snack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.service.FillContentService;
import ru.vkhackathon.snack.service.FoodCourtService;

/**
 * Created by Petr Gusarov
 */
@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestRest {
    @Autowired
    private FoodCourtService foodCourtService;
    @Autowired
    private FillContentService fillContentService;

    @RequestMapping(path = "/ping")
    public boolean testPing() {
        return true;
    }

    @RequestMapping(path = "/fill/menu/{foodId}")
    public int fillMenuForFoodCourt(@PathVariable("foodId") String foodId) {
        return fillContentService.fillMenuItemForFoodCourt(foodId);
    }

    /**
     * Заполняет город спб точками
     */
    @RequestMapping(path = "fill/spb")
    public int fillAllCitySpb() {
        return fillContentService.fillTestPoint();
    }

    @RequestMapping(path = "/fill/near/vk")
    public boolean fillVkHack() {
        fillContentService.fillCustomSquare(GpsPoint.build(59.9339654, 30.3032489), 250, 10);
        return true;
    }

    @RequestMapping(path = "/fill/min")
    public boolean fillMinForTest(){
        return fillContentService.minFill();
    }
}
