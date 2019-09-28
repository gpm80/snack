package ru.vkhackathon.snack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vkhackathon.snack.FoodCourt;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.service.FoodCourtService;

import java.util.List;

/**
 * REST для запросов фудкортов
 * Created by Petr Gusarov
 */
@RestController
@RequestMapping(path = "/food", produces = MediaType.APPLICATION_JSON_VALUE)
public class FoodCourtRest {

    @Autowired
    private FoodCourtService foodCourtService;

    /**
     * Возвращает фудкорты в вказанном радиусе
     *
     * @param lat    широта
     * @param lon    долгота
     * @param radius радиус в метрах
     * @return
     */
    @RequestMapping(path = {
            "/get/near/{lat}/{lon}/{radius}",
            "/get/near/{lat}/{lon}"
    })
    @ResponseBody
    public List<FoodCourt> getNearFoodByLocation(
            @PathVariable("lat") double lat,
            @PathVariable("lon") double lon,
            @PathVariable(value = "radius", required = false) Integer radius) {
        return foodCourtService.findNearFood(GpsPoint.build(lat, lon), radius == null ? 200 : radius);
    }

    @RequestMapping(path = "/get/in/trc/{trcId}")
    public List<FoodCourt> getInTrc(@PathVariable("trcId") String trcId) {
        return foodCourtService.findByTrcId(trcId);
    }
}
