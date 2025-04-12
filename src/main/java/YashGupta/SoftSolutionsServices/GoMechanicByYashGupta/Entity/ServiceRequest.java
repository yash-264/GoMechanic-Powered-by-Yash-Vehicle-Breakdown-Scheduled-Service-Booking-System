package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Status;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "OnWay ServiceRequests & History")
public class ServiceRequest {


    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String requestnumber;

    @NonNull
    private String userid;

    @NonNull
    private String customername;

    @NonNull
    private String serviceprovidername;

    @NonNull
    private String serviceproviderid;

    @NonNull
    private String curraddress;

    @NonNull
    private String area;

    @NonNull
    private String nearby;

    @NonNull
    private String typeofvehicle;

    @NonNull
    private String vehiclenumber;


    private String servicetype;

    @NonNull
    private LocalDateTime servicerequestdatetime;


    @NonNull
    private String usercontact;

    @NonNull
    private Status status=Status.PENDING;



    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public @NonNull String getRequestnumber() {
        return requestnumber;
    }

    public void setRequestnumber(@NonNull String requestnumber) {
        this.requestnumber = requestnumber;
    }

    public @NonNull String getUserid() {
        return userid;
    }

    public void setUserid(@NonNull String userid) {
        this.userid = userid;
    }

    public @NonNull String getCustomername() {
        return customername;
    }

    public void setCustomername(@NonNull String customername) {
        this.customername = customername;
    }

    public @NonNull String getServiceprovidername() {
        return serviceprovidername;
    }

    public void setServiceprovidername(@NonNull String serviceprovidername) {
        this.serviceprovidername = serviceprovidername;
    }

    public @NonNull String getServiceproviderid() {
        return serviceproviderid;
    }

    public void setServiceproviderid(@NonNull String serviceproviderid) {
        this.serviceproviderid = serviceproviderid;
    }

    public @NonNull String getCurraddress() {
        return curraddress;
    }

    public void setCurraddress(@NonNull String curraddress) {
        this.curraddress = curraddress;
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

    public @NonNull String getTypeofvehicle() {
        return typeofvehicle;
    }

    public void setTypeofvehicle(@NonNull String typeofvehicle) {
        this.typeofvehicle = typeofvehicle;
    }

    public @NonNull String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(@NonNull String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public @NonNull LocalDateTime getServicerequestdatetime() {
        return servicerequestdatetime;
    }

    public void setServicerequestdatetime(@NonNull LocalDateTime servicerequestdatetime) {
        this.servicerequestdatetime = servicerequestdatetime;
    }

    public @NonNull String getUsercontact() {
        return usercontact;
    }

    public void setUsercontact(@NonNull String usercontact) {
        this.usercontact = usercontact;
    }


    public @NonNull Status getStatus() {
        return status;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }
}
