package ru.vkhackathon.snack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.FoodCourt;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.MenuItem;
import ru.vkhackathon.snack.Trc;
import ru.vkhackathon.snack.common.GpsCalculator;
import ru.vkhackathon.snack.common.ResFileUtils;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Сервис для загрузки контента
 */
@Service
public class TestFillContentService {

    @Autowired
    private TrcService trcService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private FoodCourtService foodCourtService;
    @Autowired
    private MenuGroupService menuGroupService;
    @Autowired
    private MenuItemService menuItemService;

    public boolean fillDb() throws Exception {
        Random rnd = new Random();
        Trc[] trcs = ResFileUtils.loadFromFile("json/trc.json", Trc[].class);
        Brand[] brands = ResFileUtils.loadFromFile("json/brand.json", Brand[].class);
        // Сохраним бренды
        List<Brand> saveBrands = Stream.of(brands).map(brand -> {
            File file = ResFileUtils.getFile(brand.getImageUri());
            return brandService.save(brand, file);
        }).collect(Collectors.toList());

        for (Trc trc : trcs) {
            Trc saveTrc = trcService.save(trc);
            {// Привяжем к нему фудкорты
                int i = 3;
                while (i-- > 0) {
                    int indBrand = rnd.nextInt(saveBrands.size());
                    Brand brand = saveBrands.get(indBrand);
                    FoodCourt foodCourt = new FoodCourt();
                    foodCourt.setBrand(brand);
                    foodCourt.setTrc(saveTrc);

                    foodCourt.setTitle(brand.getTitle());
                    foodCourt.setDescription(brand.getDescription());

                    GpsPoint point = GpsCalculator.getRandomInSquare(saveTrc, 20);
                    foodCourt.setLatitude(point.getLatitude());
                    foodCourt.setLongitude(point.getLongitude());
                    FoodCourt food = foodCourtService.save(foodCourt);
                    {
                        int groupCount = rnd.nextInt(4 - 3) + 3;
                        MenuGroup[] menuGroups = ResFileUtils.loadFromFile("json/group.json", MenuGroup[].class);
                        while (groupCount-- > 0) {
                            MenuGroup selectGroup = menuGroups[rnd.nextInt(menuGroups.length)];
                            selectGroup.setId(null);
                            selectGroup.setFoodCourt(food);
                            MenuItem[] menuItems = ResFileUtils.loadFromFile("json/menu.json", MenuItem[].class);
                            int menuCount = rnd.nextInt(4 - 2) + 2;
                            while (menuCount-- > 0) {
                                MenuItem selectMenuItem = menuItems[rnd.nextInt(menuItems.length)];
                                selectMenuItem.setId(null);
                                selectMenuItem.setParentFoodCourt(food);
                                selectGroup.getMenuItems().add(menuItemService.save(
                                        selectMenuItem, food.getId(), ResFileUtils.getFile(selectMenuItem.getImageUri()))
                                );
                            }
                            menuGroupService.save(selectGroup, food.getId(), ResFileUtils.getFile(selectGroup.getImageUri()));
                        }
                    }

                }
            }
        }
        return true;
    }
}
