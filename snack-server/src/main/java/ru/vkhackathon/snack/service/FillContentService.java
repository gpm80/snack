package ru.vkhackathon.snack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.FoodCourt;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.MenuGroup;
import ru.vkhackathon.snack.MenuItem;
import ru.vkhackathon.snack.Special;
import ru.vkhackathon.snack.Trc;
import ru.vkhackathon.snack.common.GpsCalculator;
import ru.vkhackathon.snack.common.ResFileUtils;
import ru.vkhackathon.snack.domain.BrandDAO;
import ru.vkhackathon.snack.domain.TrcDAO;
import ru.vkhackathon.snack.repository.BrandRepository;
import ru.vkhackathon.snack.repository.FoodCourtRepository;
import ru.vkhackathon.snack.repository.MenuItemRepository;
import ru.vkhackathon.snack.repository.TrcRepository;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Petr Gusarov
 */
@Service
public class FillContentService {

    private static Logger logger = LoggerFactory.getLogger(FillContentService.class);
    @Autowired
    private FoodCourtRepository foodRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuGroupService menuGroupService;
    @Autowired
    private TrcRepository trcRepository;


    private List<Brand> createBrands() {
        List<Brand> brands = brandRepository.findAll().stream()
                .map(BrandDAO::syncGetBean)
                .collect(Collectors.toList());
        if (!brands.isEmpty()) {
            return null;
        }
        Brand brand = new Brand();
        brand.setTitle("Мексиканец");
        brand.setDescription("Мексиканская кухня");
        brands.add(brandRepository.save(brand, ResFileUtils.getFile("images", "foodMexico.jpg")));

        brand = new Brand();
        brand.setTitle("Морковка фуд");
        brand.setDescription("Вегетарианская кухня");
        brands.add(brandRepository.save(brand, ResFileUtils.getFile("images", "foodVegan.jpg")));

        brand = new Brand();
        brand.setTitle("Здоровый фасфуд");
        brand.setDescription("Кухня здорового питания");
        brands.add(brandRepository.save(brand, ResFileUtils.getFile("images", "foodEco.jpg")));

        brand = new Brand();
        brand.setTitle("Бутер На");
        brand.setDescription("Бутерброды шавухи и Ко");
        brands.add(brandRepository.save(brand, ResFileUtils.getFile("images", "foodBurger.jpg")));

        return brands;
    }


    /**
     * Заполняет точками город СПБ
     */
    public int fillTestPoint() {
        List<Brand> brands = createBrands();
        //60.022022, 30.222634 СПБ (верх левый)
        // 12 ->   16 вниз
        int left = 12;
        int bottom = 16;
        Random random = new Random();
        GpsPoint startPoint = GpsPoint.build(60.022022, 30.222634);
        int radius = 500;
        int counter = 0;
        for (int y = 0; y < bottom; y++) {
            GpsPoint yOfRange = GpsCalculator.getEndPointOfRange(startPoint, radius * 2 * y, 180);
            for (int x = 0; x < left; x++) {
                GpsPoint xOfRange = GpsCalculator.getEndPointOfRange(startPoint, radius * 2 * x, 90);
                int countPoint = random.nextInt(20 - 10) + 10;
                logger.info("point:'{}/{}/{}' (count: {})", yOfRange.getLatitude(), xOfRange.getLongitude(), radius, countPoint);
                while (countPoint-- > 0) {
                    // Случайная точка в квадрате
                    GpsPoint randomPoint = GpsCalculator.getRandomInSquare(
                            GpsPoint.build(yOfRange.getLatitude(), xOfRange.getLongitude()),
                            radius);
                    foodRepository.save(createFoodCourtRandom(randomPoint, brands.get(random.nextInt(brands.size()))));
                    counter++;
                }
            }
        }
        logger.info("Созданно {} фудкортов", counter);
        return counter;
    }

    /**
     * Создает фудкорт в указанной точке
     *
     * @param position
     * @param brand
     * @return
     */
    private FoodCourt createFoodCourtRandom(GpsPoint position, Brand brand) {
        if (brand != null) {
            throw new IllegalArgumentException("brands empty");
        }
        Random random = new Random();
        FoodCourt f = new FoodCourt();
        f.setBrand(brand);
        f.setTitle(f.getBrand().getTitle() + ":" + position);
        f.setLatitude(position.getLatitude());
        f.setLongitude(position.getLongitude());
        f.getSpecialSet().add(Special.values()[random.nextInt(Special.values().length)]);
        return f;
    }

    /**
     * Заполняет меню фудкорта случайными записями если там пусто
     *
     * @param foodId
     * @return
     */
    public int fillMenuItemForFoodCourt(final String foodId) {
        FoodCourt one = foodRepository.findOne(foodId);
        if (one == null) {
            return -1;
        }
        List<MenuItem> byFoodId = menuItemRepository.findByFoodId(foodId, 2);
        if (!byFoodId.isEmpty()) {
            return 0;
        }
        // Заполняем
        String[] titles = {"Бургер", "Напиток газированный", "Мороженное", "Шаверма", "Шаурма",
                "Картошка", "Лист салата"};
        String[] descrips = {"Вкусный нежный", "Очень острый", "С запахом фиалок", "Без мяса птицы",
                "С пузыриками", "Для настоящих гурманов", "Съедобный", "С овощной начинкой"};
        int[] prices = {9900, 18900, 4698, 1589, 16600, 8900, 10099};
        int count = 25, total = 0;
        Random rnd = new Random();
        while (count-- > 0) {
            MenuItem menuItem = new MenuItem();
            menuItem.setTitle(titles[rnd.nextInt(titles.length)]);
            menuItem.setDescription(descrips[rnd.nextInt(descrips.length)]);
            menuItem.setPrice(prices[rnd.nextInt(prices.length)]);
            menuItem.getSpecialSet().add(Special.values()[rnd.nextInt(Special.values().length)]);
            menuItemRepository.save(menuItem, foodId, null);
            total++;
        }
        return total;
    }

    public void fillCustomSquare(GpsPoint center, int radius, int count) {
        List<Brand> brands = createBrands();
        Random rnd = new Random();
        Brand brand = brands.get(rnd.nextInt(brands.size()));
        GpsPoint randomInSquare = GpsCalculator.getRandomInSquare(center, radius);
        foodRepository.save(createFoodCourtRandom(randomInSquare, brand));
    }

    /**
     * Минимально необходимое заполнение базы
     *
     * @return
     */
    public boolean minFill() {
        List<Brand> brands = createBrands();
        if (brands == null) {
            return false;
        }
        Random rnd = new Random();
        {// Создать ТРЦ1
            GpsPoint point = GpsPoint.build(59.9339654, 30.3032489);
            Trc trc = Builder.createTrc("У Манежа", "Большой и модный", point);
            TrcDAO savedTrc = trcRepository.save(trc);
            {// Привяжем к нему фудкорты
                int i = 4;
                while (i-- > 0) {
                    Brand brand = brands.get(rnd.nextInt(brands.size()));
                    foodRepository.save(Builder.createFoodCourt("food " + brand.getTitle(), "Будет описание",
                            brand, savedTrc.getBean(), GpsCalculator.getRandomInSquare(point, 20))
                    );
                }
            }
        }
        {
            GpsPoint point = GpsPoint.build(59.935392, 30.311365);
            Trc trc2 = Builder.createTrc("Исакий", "Маленький но уютный", point);
            TrcDAO saveTrc2 = trcRepository.save(trc2);
            {// Привяжем к нему фудкорты
                int i = 4;
                while (i-- > 0) {
                    Brand brand = brands.get(rnd.nextInt(brands.size()));
                    foodRepository.save(Builder.createFoodCourt("food " + brand.getTitle(), "Бужет описание",
                            brand, saveTrc2.getBean(), GpsCalculator.getRandomInSquare(point, 20))
                    );
                }
            }
        }
        {
            GpsPoint point = GpsPoint.build(60.005341, 30.301262);
            Trc trc = Builder.createTrc("Студенческий", "Большой, удобный и недорогой", point);
            TrcDAO saveTrc = trcRepository.save(trc);
            {// Привяжем к нему фудкорты
                int i = 4;
                while (i-- > 0) {
                    Brand brand = brands.get(rnd.nextInt(brands.size()));
                    FoodCourt food = foodRepository.save(Builder.createFoodCourt("food " + brand.getTitle(), "Будет описание",
                            brand, saveTrc.getBean(), GpsCalculator.getRandomInSquare(point, 20))
                    );
                    addMenuAndGroup(food.getId());
                }
            }
        }
        // Привязать к ним фудкотры
        // К кждому привязать меню
        return true;
    }

    /**
     * Сохраняет группы меню и меню в базу
     *
     * @param foodId
     */
    private void addMenuAndGroup(String foodId) {
        Objects.requireNonNull(foodId, "foodId must'd be null");
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setTitle("Наборчик школьника");
        menuGroup.setDescription("Для отличников");
        {
            MenuItem menuItem = new MenuItem();
            menuItem.setTitle("Пицца Школьна");
            menuItem.setDescription("15 см");
            menuItem.setKkal(700);
            menuGroup.getMenuItems().add(menuItemRepository.save(menuItem, foodId, null).syncGetBean());
            //
            menuItem = new MenuItem();
            menuItem.setTitle("Напиток сладкий");
            menuItem.setDescription("250 мл");
            menuItem.setKkal(200);
            menuGroup.getMenuItems().add(menuItemRepository.save(menuItem, foodId, null).syncGetBean());
            //
            menuItem = new MenuItem();
            menuItem.setTitle("Картошка с перцем");
            menuItem.setDescription("Остренькая");
            menuItem.setKkal(350);
            menuGroup.getMenuItems().add(menuItemRepository.save(menuItem, foodId, null).syncGetBean());
        }
        menuGroupService.save(menuGroup, foodId, null);
    }

    private static class Builder {

        static Trc createTrc(String title, String desc, GpsPoint point) {
            Trc trc = new Trc();
            trc.setTitle(title);
            trc.setDescription(desc);
            trc.setLatitude(point.getLatitude());
            trc.setLongitude(point.getLongitude());
            return trc;
        }

        static FoodCourt createFoodCourt(String title, String desc, Brand brand, Trc trc, GpsPoint point) {
            FoodCourt f = new FoodCourt();
            f.setTitle(title);
            f.setDescription(desc);
            f.setBrand(brand);
            f.setTrc(trc);
            f.setLatitude(point.getLatitude());
            f.setLongitude(point.getLongitude());
            return f;
        }

    }
}
