package ru.vkhackathon.snack.common;

import org.junit.Assert;
import org.junit.Test;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.MenuItem;
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
                ResFileUtils.loadFromFile("json/group.json", MenuGroup[].class).length > 0);
        Assert.assertTrue(
                ResFileUtils.loadFromFile("json/menu.json", MenuItem[].class).length > 0);
    }

}