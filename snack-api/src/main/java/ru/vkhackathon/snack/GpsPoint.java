package ru.vkhackathon.snack;

/**
 * GPS координаты объекта
 */
public class GpsPoint {

    /**
     * Широта
     */
    private double latitude;
    /**
     * Долгота
     */
    private double longitude;

    public GpsPoint() {
    }

    /**
     * Билдер
     *
     * @param latitude  широта
     * @param longitude долгота
     * @return
     */
    public static GpsPoint build(double latitude, double longitude) {
        GpsPoint point = new GpsPoint();
        point.setLatitude(latitude);
        point.setLongitude(longitude);
        return point;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(latitude);
        sb.append(", ").append(longitude);
        return sb.toString();
    }
}
