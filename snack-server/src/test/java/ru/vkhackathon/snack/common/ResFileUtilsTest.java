package ru.vkhackathon.snack.common;

import org.junit.Assert;
import org.junit.Test;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.FoodCourt;
import ru.vkhackathon.snack.Trc;

/**
 * Created by Petr Gusarov
 */
public class ResFileUtilsTest {

    @Test
    public void testJsonParser() throws Exception {
        Assert.assertTrue(
                ResFileUtils.loadFromFile("json/brand.json", Brand[].class).length > 0);
        Assert.assertTrue(
                ResFileUtils.loadFromFile("json/trc.json", Trc[].class).length > 0);
        Assert.assertTrue(
                ResFileUtils.loadFromFile("json/food.json", FoodCourt[].class).length > 0);
        Assert.assertTrue(
                ResFileUtils.loadFromFile("json/food.json", FoodCourt[].class).length > 0);

    }

}