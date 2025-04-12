package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity;


import lombok.NonNull;

public class GetServiceProviderEntity {

    @NonNull
    private String address;

    @NonNull
    private String area;

    @NonNull
    private  String nearby;


    public @NonNull String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public @NonNull String getArea() {
        return area;
    }

    public void setArea(@NonNull String area) {
        this.area = area;
    }

    public @NonNull String getNearby() {
        return nearby;
    }

    public void setNearby(@NonNull String nearby) {
        this.nearby = nearby;
    }
}
