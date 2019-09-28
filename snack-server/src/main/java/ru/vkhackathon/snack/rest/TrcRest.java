package ru.vkhackathon.snack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.Trc;
import ru.vkhackathon.snack.service.TrcService;

import java.util.List;

/**
 * Created by Petr Gusarov
 */
@RestController
@RequestMapping(path = "/trc", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrcRest {

    @Autowired
    private TrcService trcService;

    @RequestMapping(path = {
            "/get/near/{lat}/{lon}/{radius}",
            "/get/near/{lat}/{lon}"
    })
    @ResponseBody
    public List<Trc> getNearFoodByLocation(
            @PathVariable("lat") double lat,
            @PathVariable("lon") double lon,
            @PathVariable(value = "radius", required = false) Integer radius) {
        return trcService.findNearByPoing(GpsPoint.build(lat, lon), radius == null ? 200 : radius);
    }

}
