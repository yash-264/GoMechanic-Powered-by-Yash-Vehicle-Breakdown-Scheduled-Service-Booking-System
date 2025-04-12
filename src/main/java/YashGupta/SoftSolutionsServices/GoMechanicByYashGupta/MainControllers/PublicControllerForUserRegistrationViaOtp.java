package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.OtpVerificationRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.User;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Role;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator.CustomerIdGenerator;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator.OtpGenerator;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.EmailServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sign/up/user")
public class PublicControllerForUserRegistrationViaOtp {

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private UserServices userServices;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private CustomerIdGenerator customerIdGenerator;


    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();
    private final Map<String, User> userStore = new ConcurrentHashMap<>();

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



    @PostMapping("/generate/otp")
    public ResponseEntity<?> generateOtp(@RequestBody User user) {
        if (user.getGmail() == null || user.getGmail().trim().isEmpty()) {
            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }

        String otp = otpGenerator.generateOtp();

        otpStore.put(user.getGmail(), new OtpData(otp));

        user.setUserid(customerIdGenerator.generateCustomerId(Role.USER));

        user.setRole(Role.USER);

        userStore.put(user.getGmail(), user);

        emailServices.sendUserOtpEmail(user.getGmail(), otp);

        return ResponseEntity.ok("OTP has been sent to your email.");
    }


    @PostMapping("/verify/otp/and/register")
    public ResponseEntity<?> verifyOtpAndRegister(@RequestBody OtpVerificationRequest request) {
        OtpData otpData = otpStore.get(request.getGmail());

        if (otpData == null || isExpired(otpData)) {
            return new ResponseEntity<>("OTP expired or invalid. Please try again.", HttpStatus.UNAUTHORIZED);
        }

        if (!otpData.getOtp().equals(request.getOtp())) {
            return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED);
        }

        User user = userStore.get(request.getGmail());
        if (user == null) {
            return new ResponseEntity<>("User details not found. Please try again.", HttpStatus.BAD_REQUEST);
        }

        userServices.saveUser(user);

        emailServices.sendUserSignupEmail(user.getGmail(),user.getUsername());


        otpStore.remove(request.getGmail());
        userStore.remove(request.getGmail());


        return new ResponseEntity<>(
                " Registration successful! Your User ID is: " + user.getUserid() +
                        "\n   A confirmation email has been sent to your registered email ID.",
                HttpStatus.CREATED
        );

    }


    private boolean isExpired(OtpData otpData) {
        return System.currentTimeMillis() - otpData.getTimestamp() > OTP_VALIDITY_DURATION_MS;
    }

}

