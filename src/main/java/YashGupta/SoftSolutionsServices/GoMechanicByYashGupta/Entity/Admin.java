package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity;

import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Role;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admins")
public class Admin {


    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String adminid;

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

    public @NonNull String getAdminid() {
        return adminid;
    }

    public void setAdminid(@NonNull String adminid) {
        this.adminid = adminid;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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
}

