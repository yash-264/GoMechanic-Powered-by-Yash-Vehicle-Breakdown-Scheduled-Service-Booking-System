package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Role;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "ServiceProviders")
public class ServiceProvider {


    @Id
    private ObjectId id;


    @NonNull
    @Indexed(unique = true)
    private String serviceproviderid;

    @NonNull
    private String businessname;

    @NonNull
    private String businessaddress;

    @NonNull
    private String area;

    @NonNull
    private String nearby;

    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;


    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    @Indexed(unique = true)
    private String gmail;

    @NonNull
    @Indexed(unique = true)
    private String phonenumber;

    @NonNull
    private Role role;



    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public @NonNull String getServiceproviderid() {
        return serviceproviderid;
    }

    public void setServiceproviderid(@NonNull String serviceproviderid) {
        this.serviceproviderid = serviceproviderid;
    }

    public @NonNull String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(@NonNull String businessname) {
        this.businessname = businessname;
    }

    public @NonNull String getBusinessaddress() {
        return businessaddress;
    }

    public void setBusinessaddress(@NonNull String businessaddress) {
        this.businessaddress = businessaddress;
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

    public @NonNull String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public @NonNull String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public @NonNull String getGmail() {
        return gmail;
    }

    public void setGmail(@NonNull String gmail) {
        this.gmail = gmail;
    }

    public @NonNull String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(@NonNull String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public @NonNull Role getRole() {
        return role;
    }

    public void setRole(@NonNull Role role) {
        this.role = role;
    }


    @Transient
    private Double distanceinkm;

    public Double getDistanceinkm() {
        return distanceinkm;
    }

    public void setDistanceinkm(Double distanceinkm) {
        this.distanceinkm = distanceinkm;
    }
}
