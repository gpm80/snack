package ru.vkhackathon.snack.common;

import ru.vkhackathon.snack.GpsPoint;

/**
 * Квадратный диапазон точек
 * Created by Petr Gusarov
 */
public class GpsSquare {

    private GpsPoint top;
    private GpsPoint bottom;
    private GpsPoint left;
    private GpsPoint right;

    public GpsSquare() {
    }

    public double topLatitude() {
        return getTop().getLatitude();
    }

    public double bottomLatitude() {
        return getBottom().getLatitude();
    }

    public double leftLongitude() {
        return getLeft().getLongitude();
    }

    public double rightLongitude() {
        return getRight().getLongitude();
    }

    private GpsPoint getIsNull(GpsPoint gpsPoint) {
        if (gpsPoint == null) {
            return GpsPoint.build(0, 0);
        }
        return gpsPoint;
    }

    public GpsPoint getTop() {
        return getIsNull(top);
    }

    public void setTop(GpsPoint top) {
        this.top = top;
    }

    public GpsPoint getBottom() {
        return getIsNull(bottom);
    }

    public void setBottom(GpsPoint bottom) {
        this.bottom = bottom;
    }

    public GpsPoint getLeft() {
        return getIsNull(left);
    }

    public void setLeft(GpsPoint left) {
        this.left = left;
    }

    public GpsPoint getRight() {
        return getIsNull(right);
    }

    public void setRight(GpsPoint right) {
        this.right = right;
    }
}
