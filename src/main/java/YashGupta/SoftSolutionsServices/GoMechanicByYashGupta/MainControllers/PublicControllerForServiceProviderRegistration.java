package YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.MainControllers;


import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.OtpVerificationRequest;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Entity.ServiceProvider;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Enums.Role;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator.CustomerIdGenerator;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Generator.OtpGenerator;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.EmailServices;
import YashGupta.SoftSolutionsServices.GoMechanicByYashGupta.Services.ServiceProviderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sign/up/service/provider")
public class PublicControllerForServiceProviderRegistration {

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private ServiceProviderServices serviceProviderServices;

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private CustomerIdGenerator customerIdGenerator;

    private final Map<String, OtpData> otpStore = new ConcurrentHashMap<>();
    private final Map<String, ServiceProvider> serviceProviderStore = new ConcurrentHashMap<>();

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
    public ResponseEntity<?> generateOtp(@RequestBody ServiceProvider sp) {
        if (sp.getGmail() == null || sp.getGmail().trim().isEmpty()) {
            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }

        String otp = otpGenerator.generateOtp();

        otpStore.put(sp.getGmail(), new OtpData(otp));


        sp.setServiceproviderid(customerIdGenerator.generateCustomerId(Role.SERVICEPROVIDER));

        sp.setRole(Role.SERVICEPROVIDER);

        serviceProviderStore.put(sp.getGmail(), sp);

        emailServices.sendServiceProviderOtpEmail(sp.getGmail(), otp);

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

        ServiceProvider sp = serviceProviderStore.get(request.getGmail());

        if (sp == null) {

            return new ResponseEntity<>("Service provider details not found. Please try again.", HttpStatus.BAD_REQUEST);
        }

        serviceProviderServices.saveServiceProvider(sp);

        otpStore.remove(request.getGmail());

        serviceProviderStore.remove(request.getGmail());

        emailServices.sendServiceProviderCreationEmail(sp.getGmail(),sp.getUsername());


        return new ResponseEntity<>(
                " Registration successful! Your Service Provider ID is: " + sp.getServiceproviderid() +
                        "\n   A confirmation email has been sent to your registered email ID.",
                HttpStatus.CREATED
        );

    }


    private boolean isExpired(OtpData otpData) {
        return System.currentTimeMillis() - otpData.getTimestamp() > OTP_VALIDITY_DURATION_MS;
    }



}
