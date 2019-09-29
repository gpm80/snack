package ru.vkhackathon.snack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vkhackathon.snack.Brand;
import ru.vkhackathon.snack.FoodCourt;
import ru.vkhackathon.snack.GpsPoint;
import ru.vkhackathon.snack.Special;
import ru.vkhackathon.snack.common.GpsCalculator;
import ru.vkhackathon.snack.domain.FoodCourtDAO;
import ru.vkhackathon.snack.repository.FoodCourtRepository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Petr Gusarov
 */
@Service
public class FoodCourtService {

    private static Logger logger = LoggerFactory.getLogger(FoodCourtService.class);
    @Autowired
    private FoodCourtRepository repository;

    /**
     * Добавляет случанные фудкорты в радиусе
     *
     * @param nameFood название бренда
     * @param center   сентр области
     * @param radius   радиус
     * @param count    количество
     * @return
     */
    public boolean addRandomFoodCourt(String nameFood, GpsPoint center, int radius, int count) {
        Random random = new Random();
        int counter = count;
        while (counter-- > 0) {
            FoodCourt f = new FoodCourt();
            f.setBrand(new Brand());
            int rnd = random.nextInt(count * 10);
            f.getBrand().setTitle(nameFood + "-" + rnd);
            f.setTitle("Описание точки " + f.getBrand().getTitle());
            GpsPoint randomLocation = GpsCalculator.getRandomInSquare(center, radius);
            f.setLatitude(randomLocation.getLatitude());
            f.setLongitude(randomLocation.getLongitude());
            f.getSpecialSet().add(Special.VEGAN);
            repository.save(f);
        }
        return true;
    }

    /**
     * Возвращает фудкорты в указанном радиусе относительно позиции
     *
     * @param location позиция
     * @param radius   радиус в метрах
     * @return
     */
    public List<FoodCourt> findNearFood(GpsPoint location, int radius) {
        List<FoodCourt> nearFoodCourt = repository.getNearFoodCourt(location, radius);
        logger.debug("found food courts by hash {}", nearFoodCourt.size());
        // Убираем невошедшие в квадрат по хешу
        List<FoodCourt> foodCourtsFiltered = nearFoodCourt.stream()
                .filter(fc -> GpsCalculator.isInSquareArea(fc, location, radius))
                .collect(Collectors.toList());
        logger.debug("filtered food courts by radius {}", foodCourtsFiltered.size());
        return foodCourtsFiltered;
    }

    public List<FoodCourt> findByTrcId(String trcId) {
        return repository.findByTrcId(trcId).stream()
                .map(FoodCourtDAO::syncGetBean)
                .collect(Collectors.toList());
    }

    public FoodCourt save(FoodCourt foodCourt) {
        return repository.save(foodCourt);
    }
}
