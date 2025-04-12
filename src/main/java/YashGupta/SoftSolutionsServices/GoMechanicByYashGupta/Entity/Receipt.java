package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Status;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ServiceReceipts")
public class Receipt {


    @Id
    private ObjectId id;

    @NonNull
    private String requestnumber;

    @NonNull
    private String userid;

    @NonNull
    private String customername;

    @NonNull
    private String serviceproviderid;

    @NonNull
    private String mechanicname;

    @NonNull
    private String vehiclenumber;

    @NonNull
    private String servicetype;

    @NonNull
    private String servicedescription;

    @NonNull
    private double servicecost;

    @NonNull
    private LocalDateTime servicerequestdate;


    @NonNull
    private Status status;


    @NonNull
    private LocalDateTime servicecompletiondatetime;










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

    public @NonNull String getServiceproviderid() {
        return serviceproviderid;
    }

    public void setServiceproviderid(@NonNull String serviceproviderid) {
        this.serviceproviderid = serviceproviderid;
    }

    public @NonNull String getMechanicname() {
        return mechanicname;
    }

    public void setMechanicname(@NonNull String mechanicname) {
        this.mechanicname = mechanicname;
    }

    public @NonNull String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(@NonNull String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public @NonNull String getServicetype() {
        return servicetype;
    }

    public void setServicetype(@NonNull String servicetype) {
        this.servicetype = servicetype;
    }

    public @NonNull String getServicedescription() {
        return servicedescription;
    }

    public void setServicedescription(@NonNull String servicedescription) {
        this.servicedescription = servicedescription;
    }

    @NonNull
    public double getServicecost() {
        return servicecost;
    }

    public void setServicecost(@NonNull double servicecost) {
        this.servicecost = servicecost;
    }

    public @NonNull LocalDateTime getServicerequestdate() {
        return servicerequestdate;
    }

    public void setServicerequestdate(@NonNull LocalDateTime servicerequestdate) {
        this.servicerequestdate = servicerequestdate;
    }

    public @NonNull Status getStatus() {
        return status;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }


    public @NonNull LocalDateTime getServicecompletiondatetime() {
        return servicecompletiondatetime;
    }

    public void setServicecompletiondatetime(@NonNull LocalDateTime servicecompletiondatetime) {
        this.servicecompletiondatetime = servicecompletiondatetime;
    }
}
