package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity;

import lombok.NonNull;

public class OtpVerificationRequest {


    @NonNull
    private String gmail;

    @NonNull
    private String otp;


    public @NonNull String getGmail() {
        return gmail;
    }

    public void setGmail(@NonNull String gmail) {
        this.gmail = gmail;
    }

    public @NonNull String getOtp() {
        return otp;
    }

    public void setOtp(@NonNull String otp) {
        this.otp = otp;
    }

}
