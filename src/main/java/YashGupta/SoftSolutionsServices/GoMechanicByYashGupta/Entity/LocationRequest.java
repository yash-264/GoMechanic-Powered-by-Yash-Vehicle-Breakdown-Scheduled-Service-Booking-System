package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity;

import lombok.NonNull;

public class LocationRequest {


    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

    @NonNull
    private double radiusinkm=10;

    @NonNull
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull Double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull Double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public double getRadiusinkm() {
        return radiusinkm;
    }

    public void setRadiusinkm(@NonNull Double radiusinkm) {
        this.radiusinkm = radiusinkm;
    }
}
