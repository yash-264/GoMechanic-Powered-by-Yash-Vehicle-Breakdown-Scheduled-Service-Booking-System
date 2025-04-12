package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.Admin;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.OtpVerificationRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Role;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator.CustomerIdGenerator;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator.OtpGenerator;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.AdminServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.EmailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("sign/up/admin")
public class publicControllerForAdminCreationViaOtp {



    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private AdminServices adminServices;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private CustomerIdGenerator customerIdGenerator;


    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();
    private final Map<String, Admin> adminStore = new ConcurrentHashMap<>();

    private static final long OTP_VALIDITY_DURATION_MS = 5 * 60 * 1000; // 5 minutes

    static class OtpData {
        private final String otp;
        private final long timestamp;

        public OtpData(String otp) {
            this.otp = otp;
            this.timestamp = System.currentTimeMillis();
        }

        public String getOtp() {
            return otp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }


    @Value("${super.admin.gmail}")
    private String superAdminGmail;


    @PostMapping("/generate/admin/creation/key")
    public ResponseEntity<?> generateOtp(@RequestBody Admin admin) {

        if (admin.getGmail() == null || admin.getGmail().trim().isEmpty()) {

            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }

        String otp = otpGenerator.generateOtp();

        otpStore.put(admin.getGmail(), new OtpData(otp));

        admin.setAdminid(customerIdGenerator.generateCustomerId(Role.ADMIN));
        admin.setRole(Role.ADMIN);
        adminStore.put(admin.getGmail(), admin);

        emailServices.sendAdminOtpEmail(superAdminGmail, otp ,admin.getUsername());

        return ResponseEntity.ok("Key has been sent to SuperAdmin email.");
    }


    @PostMapping("/verify/key/and/register")
    public ResponseEntity<?> verifyOtpAndRegister(@RequestBody OtpVerificationRequest request) {
        OtpData otpData = otpStore.get(request.getGmail());

        if (otpData == null || isExpired(otpData)) {
            return new ResponseEntity<>("OTP expired or invalid. Please try again.", HttpStatus.UNAUTHORIZED);
        }

        if (!otpData.getOtp().equals(request.getOtp())) {
            return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED);
        }

        Admin admin = adminStore.get(request.getGmail());
        if (admin == null) {
            return new ResponseEntity<>("Admin details not found. Please try again.", HttpStatus.BAD_REQUEST);
        }

        adminServices.saveAdmin(admin);

        otpStore.remove(request.getGmail());
        adminStore.remove(request.getGmail());

        emailServices.sendAdminCreationEmail(admin.getGmail(), admin.getUsername());

        return new ResponseEntity<>(
                "Registration successful! Your Admin ID is: " + admin.getAdminid() +
                        "\nA confirmation email has been sent to your registered email ID.",
                HttpStatus.CREATED
        );
    }


    private boolean isExpired(OtpData otpData) {
        return System.currentTimeMillis() - otpData.getTimestamp() > OTP_VALIDITY_DURATION_MS;
    }


}
