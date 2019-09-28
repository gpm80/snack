package ru.vkhackathon.snack.common;

import ch.hsr.geohash.GeoHash;
import org.junit.Assert;
import org.junit.Test;
import ru.vkhackathon.snack.GpsPoint;

import static org.junit.Assert.*;

/**
 * Created by Petr Gusarov
 */
public class GpsCalculatorTest {
    @Test
    public void getDistanceBetweenTwoPoints() throws Exception {
        double distance = GpsCalculator.getDistanceBetweenTwoPoints(
            GpsPoint.build(59.977248, 30.345641),
            GpsPoint.build(59.974622, 30.349017)
        );
        System.out.println("distance = " + distance);
        Assert.assertTrue(GpsCalculator
            .isInCircleArea(
                GpsPoint.build(59.974622, 30.349017),
                GpsPoint.build(59.977248, 30.345641),
                distance + 1)
        );
    }

    @Test
    public void testGeoHashCode() {
        String s = GeoHash.geoHashStringWithCharacterPrecision(59.977248, 30.345641, 12);
        System.out.println("s = " + s);
    }

    @Test
    public void testGeoHashRange() {
        GpsPoint point = GpsPoint.build(59.985121, 30.344522);
        String[] strings = GpsCalculator.rangeGeoHash(point, 500);
        System.out.println(strings[0]);
        System.out.println(strings[1]);
        System.out.println(strings[2]);
    }

    /**
     * Тест автогенератора точек в заданном квадрате
     */
    @Test
    public void testRandomGpsPoint() {
        GpsPoint center = GpsPoint.build(59.935752, 30.315650);
        System.out.println(GpsCalculator.geoHash(center) + "\t" + center.toString());
        int i = 100;
        while (i-- > 0) {
            GpsPoint rnd = GpsCalculator.getRandomInSquare(center, 200);
            System.out.println(GpsCalculator.geoHash(rnd) + "\t" + rnd.toString());
            Assert.assertTrue("Точка вне области!", GpsCalculator.isInSquareArea(rnd, center, 200));
        }
    }

    @Test
    public void testEndPointToRange() {
        GpsPoint endPoint = GpsCalculator.getEndPointOfRange(GpsPoint.build(60.000001, 30.000001), 500, 90);
        System.out.println("endPoint = " + endPoint);
        System.out.println("endPoint 0 = " + GpsCalculator.getEndPointOfRange(GpsPoint.build(60.000001, 30.000001), 0, 90));
    }

}