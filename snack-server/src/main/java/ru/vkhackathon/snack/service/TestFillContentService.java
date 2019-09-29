package ru.vkhackathon.snack.service;

import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.FoodCourt;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.MenuItem;
import ru.vkhackathon.snack.Trc;
import ru.vkhackathon.snack.common.ResFileUtils;

/**
 * Сервис для загрузки контента
 */
public class TestFillContentService {


    public boolean fillDb() throws Exception {
        Brand[] brands = ResFileUtils.loadFromFile("json/brand.json", Brand[].class);
        Trc[] trcs = ResFileUtils.loadFromFile("json/trc.json", Trc[].class);
        MenuGroup[] menuGroups = ResFileUtils.loadFromFile("json/group.json", MenuGroup[].class);
        MenuItem[] menuItems = ResFileUtils.loadFromFile("json/menu.json", MenuItem[].class);
        return true;
    }
}
