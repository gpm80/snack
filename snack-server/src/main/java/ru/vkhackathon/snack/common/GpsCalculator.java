package ru.vkhackathon.snack.common;

import ch.hsr.geohash.GeoHash;
import org.apache.commons.lang3.StringUtils;
import ru.vkhackathon.snack.GpsPoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Petr Gusarov
 */
public class GpsCalculator {

    /**
     * Радиус земли в метрах
     */
    private static final double EARTH_RADIUS = 6371000;
    private static final char[] char_base32 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final Map<Character, Integer> base32_map;

    static {
        base32_map = new HashMap<>();
        for (int i = 0; i < char_base32.length; i++) {
            base32_map.put(char_base32[i], i);
        }
    }

    /**
     * Рассчитывет находиться ли точка внутри области квадрата
     *
     * @param coordinate искомая координата
     * @param center     координата центра квадрата
     * @param halfSide   длина половины стороны квадрата
     * @return true если принадлежит области, иначе false
     */
    public static boolean isInSquareArea(GpsPoint coordinate, GpsPoint center, int halfSide) {
        GpsSquare squareRange = getSquareRange(center, halfSide);
        // По ширине
        boolean latres = coordinate.getLatitude() >= squareRange.bottomLatitude()
                && coordinate.getLatitude() <= squareRange.topLatitude();
        // По высоте
        boolean longres = coordinate.getLongitude() >= squareRange.leftLongitude()
                && coordinate.getLongitude() <= squareRange.rightLongitude();
        return (latres && longres);
    }

    /**
     * Возвращает геохэш размером 12
     *
     * @param gpsPoint точка для расчета
     * @return геохэш
     */
    public static String geoHash(GpsPoint gpsPoint) {
        return GeoHash.geoHashStringWithCharacterPrecision(gpsPoint.getLatitude(), gpsPoint.getLongitude(), 12);
    }

    /**
     * Возвращает диапазон геохешей для выборки.
     * Последний хеш точности сдвагается на offset позиций
     *
     * @param gpsPoint точка для расчета
     * @param radius   радиус области
     * @return {@code String[3]{['полный хэш точки', 'начальный хэш', 'конечный хэш'} }
     */
    public static String[] rangeGeoHash(GpsPoint gpsPoint, int radius) {
        // точность знаков хэша
        int precision = radius > 2000 ? 4 : 5;
        // сдвиг последнего в точности сивола
        int offset = radius > 2000 ? 0 : 2;
        String hash = GpsCalculator.geoHash(gpsPoint);
        Integer integer = base32_map.get(hash.charAt(precision - 1));
        char prev, next;
        prev = char_base32[integer - offset < 0 ? 0 : integer - offset];
        next = char_base32[integer + offset > 31 ? 31 : integer + offset];
        String prefix = StringUtils.substring(hash, 0, precision - 1);

        String sufixStart = StringUtils.repeat(char_base32[0], 12 - precision);
        String sufixEnd = StringUtils.repeat(char_base32[31], 12 - precision);

        return new String[]{
                hash,
                prefix + prev + sufixStart,
                prefix + next + sufixEnd

        };
    }

    /**
     * Рассчитывет квадрат диапазона поиска координат
     *
     * @param center центр квадрата
     * @param radius половина стороны квадрата
     * @return Квадрат диапазона
     */
    public static GpsSquare getSquareRange(GpsPoint center, int radius) {
        GpsSquare gpsSquare = new GpsSquare();
        gpsSquare.setRight(getEndPointOfRange(center, radius, 90));
        gpsSquare.setLeft(getEndPointOfRange(center, radius, 270));
        gpsSquare.setTop(getEndPointOfRange(center, radius, 0));
        gpsSquare.setBottom(getEndPointOfRange(center, radius, 180));
        return gpsSquare;
    }

    /**
     * Возвращает случайную точку в квадрате
     *
     * @param center центр квадрата
     * @param radius половина стороны квадрата (радиус вписанной окружности)
     * @return случайнач точка области
     */
    public static GpsPoint getRandomInSquare(GpsPoint center, int radius) {
        GpsSquare squareRange = getSquareRange(center, radius);
        int digit = 10000000;
        Random random = new Random();
        int lat[] = {(int) (squareRange.bottomLatitude() * digit), (int) (squareRange.topLatitude() * digit)};
        int lon[] = {(int) (squareRange.leftLongitude() * digit), (int) (squareRange.rightLongitude() * digit)};
        double latRnd = roundValue((double) (random.nextInt(lat[1] - lat[0]) + lat[0]) / digit, 6);
        double lonRnd = roundValue((double) (random.nextInt(lon[1] - lon[0]) + lon[0]) / digit, 6);
        return GpsPoint.build(latRnd, lonRnd);
    }

    /**
     * Рассчитывет находиться ли точка внутри области окружности
     *
     * @param coordinate искомая координата
     * @param center     центр оружности
     * @param radius     радиус оружности в метрах
     * @return true если принадлежит области, иначе false
     */
    public static boolean isInCircleArea(GpsPoint coordinate, GpsPoint center, double radius) {
        return getDistanceBetweenTwoPoints(coordinate, center) <= radius;
    }

    /**
     * Вычисляет конечную точку из заданного источника в заданном диапазоне (в метрах) и азимуте (в градусах).
     * Этот метод использует простые геометрические уравнения для расчета конечной точки.
     *
     * @param coordinate начальная точка
     * @param range      диапазон в метрах
     * @param azimuth    азимут в градусах (0-север, 90-восток и т.д.)
     * @return Конечная точка
     */
    public static GpsPoint getEndPointOfRange(GpsPoint coordinate, int range, double azimuth) {
        double latA = Math.toRadians(coordinate.getLatitude());
        double lonA = Math.toRadians(coordinate.getLongitude());
        double angularDistance = (double) range / EARTH_RADIUS;
        double trueCourse = Math.toRadians(azimuth);
        double lat = Math.asin(Math.sin(latA) * Math.cos(angularDistance) + Math.cos(latA) * Math.sin(angularDistance) * Math.cos(trueCourse));
        double dlon = Math.atan2(Math.sin(trueCourse) * Math.sin(angularDistance) * Math.cos(latA), Math.cos(angularDistance) - Math.sin(latA) * Math.sin(lat));
        double lon = ((lonA + dlon + Math.PI) % (Math.PI * 2)) - Math.PI;
        lat = roundValue(Math.toDegrees(lat), 6);
        lon = roundValue(Math.toDegrees(lon), 6);
        return GpsPoint.build(lat, lon);
    }

    /**
     * Рассчитывает расстояние между двума координатами
     *
     * @param onePoint Первая координата
     * @param twoPoint Вторая координата
     * @return расстояние между точками (в метрах)
     */
    public static double getDistanceBetweenTwoPoints(GpsPoint onePoint, GpsPoint twoPoint) {
        double dLat = Math.toRadians(twoPoint.getLatitude() - onePoint.getLatitude());
        double dLong = Math.toRadians(twoPoint.getLongitude() - onePoint.getLongitude());
        double oneLat = Math.toRadians(onePoint.getLatitude());
        double twoLat = Math.toRadians(twoPoint.getLatitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.sin(dLong / 2) * Math.sin(dLong / 2) * Math.cos(oneLat) * Math.cos(twoLat);
        double fi = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = EARTH_RADIUS * fi;
        // Округляем
        return roundValue(d, 2);
    }

    /**
     * Округление координаты
     *
     * @param value
     * @param scale
     * @return
     */
    private static double roundValue(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_EVEN).doubleValue();
    }

    @Deprecated
    public static double distanceOld(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }

//    /**
//     * Возвращает случайную точку в указанном радиусе.
//     *
//     * @param center координаты центра
//     * @param radius радиус области
//     * @return GpsPoint координаты
//     */
//    public static GpsPoint getRandomLocation(GpsPoint center, int radius) {
//        Random random = new Random();
//        // Конвертируем радиус из метров в градусы
//        double radiusInDegrees = radius / 111000f;
//        int attempt = 3;
//        while (attempt-- > 0) {
//
//            double u = random.nextDouble();
//            double v = random.nextDouble();
//            double w = radiusInDegrees * Math.sqrt(u);
//            double t = 2 * Math.PI * v;
//            double x = w * Math.cos(t);
//            double y = w * Math.sin(t);
//
//            double new_x = x / Math.cos(center.getLongitude());
//
//            double foundLatitude = new BigDecimal(new_x + center.getLatitude())
//                .setScale(6, RoundingMode.HALF_UP)
//                .doubleValue();
//            double foundLongitude = new BigDecimal(y + center.getLongitude())
//                .setScale(6, RoundingMode.HALF_UP)
//                .doubleValue();
//            if (isInCircleArea(GpsPoint.build(foundLatitude, foundLongitude), center, radius)) {
//                return GpsPoint.build(foundLatitude, foundLongitude);
//            }
//        }
//        return new GpsPoint();
//    }
}
